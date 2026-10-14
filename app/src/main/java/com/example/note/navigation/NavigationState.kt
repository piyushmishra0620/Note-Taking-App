package com.example.note.navigation

import androidx.navigation3.runtime.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.*
import androidx.compose.runtime.snapshots.*
import androidx.savedstate.compose.serialization.serializers.MutableStateSerializer
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.modules.*
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import kotlin.collections.*

class NavigationState(
    startRoute: NavKey,
    topLevelRoute: MutableState<NavKey>,
    val backStacks: Map<NavKey, NavBackStack<NavKey>>
){
    var topLevelDestination by topLevelRoute
    val stacksInUse = mutableStateListOf(startRoute)
}

@Composable
fun rememberNavigationState(startRoute: NavKey, topLevelRoutes: List<NavKey>): NavigationState {
    val topLevelRoute = rememberSerializable(
        startRoute,
        topLevelRoutes,
        configuration = config,
        serializer = MutableStateSerializer(PolymorphicSerializer(NavKey::class))
    ) {
        mutableStateOf(startRoute)
    }

    val backStacks = topLevelRoutes.associateWith { ele ->
        rememberNavBackStack(
            configuration = config,
            ele
        )
    }

    return remember(startRoute, topLevelRoute) {
        NavigationState(
            startRoute,
            topLevelRoute,
            backStacks
        )
    }
}

val config = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(Notes::class)
            subclass(Todos::class)
            subclass(Note::class)
            subclass(Search::class)
        }
    }
}

class Navigator(val state: NavigationState) {
    fun navigate(route: NavKey) {
        if (route in state.backStacks.keys) {
            state.topLevelDestination=route
            if(route==Notes && state.stacksInUse.size!=1){
                if(state.stacksInUse.lastIndexOf(route)!=0){
                    state.stacksInUse.removeAt(state.stacksInUse.lastIndexOf(route))
                }
                state.stacksInUse.add(route)
            }else if(route!=Notes){
                if(state.stacksInUse.contains(route)){
                    state.stacksInUse.remove(route)
                }
                state.stacksInUse.add(route)
            }
        }else {
            state.backStacks[state.topLevelDestination]?.add(route)
        }
    }

    fun goBack() {
        val currentStack = state.backStacks[state.topLevelDestination] ?: error("No BackStack for ${state.topLevelDestination}")
        val lastRoute = currentStack.last()
        if (lastRoute in state.backStacks.keys) {
            state.stacksInUse.removeLastOrNull()
            state.topLevelDestination = state.stacksInUse.last()
        } else {
            currentStack.removeLastOrNull()
        }
    }
}

@Composable
fun NavigationState.toEntries(entryProvider: (NavKey) -> NavEntry<NavKey>): SnapshotStateList<NavEntry<NavKey>> {
    val decoratedEntries = backStacks.mapValues { (key, stack) ->
        val decorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator<NavKey>(),
            rememberViewModelStoreNavEntryDecorator()
        )
        rememberDecoratedNavEntries(
            backStack = stack,
            entryDecorators = decorators,
            entryProvider = entryProvider
        )
    }
    return stacksInUse.flatMap { decoratedEntries[it] ?: emptyList() }.toMutableStateList()
}
