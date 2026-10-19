package com.example.note

import android.os.Bundle
import android.view.animation.*
import androidx.activity.*
import androidx.activity.compose.*
import androidx.savedstate.serialization.SavedStateConfiguration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.note.ui.theme.NoteTheme
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.ui.unit.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.draw.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.navigation3.runtime.*
import androidx.navigation3.ui.*
import androidx.navigation3.scene.*
import com.example.note.navigation.*
import com.example.note.screens.*
import com.example.note.NoteApplication
import com.example.note.viewmodels.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import com.example.note.ui.theme.fabColor
import kotlinx.serialization.modules.*
import kotlinx.serialization.*

class MainActivity : ComponentActivity() {

    private val app by lazy { application as NoteApplication }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashscreen = installSplashScreen()
        splashscreen.setOnExitAnimationListener { splashScreenView ->
            splashScreenView.view.animate().setDuration(150).alpha(0f).scaleX(0f)
                .scaleY(0f)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .withEndAction { splashScreenView.remove() }.start()
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val noteViewModel : NoteViewModel = viewModel(factory=app.noteViewModel)
            val todoViewModel:TodoViewModel = viewModel(factory=app.todoViewModel)
            val navigationState = rememberNavigationState(Notes,listOf(Notes,Todos))
            val navigator = remember{
                Navigator(navigationState)
            }
            NoteTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                            AnimatedVisibility(visible=(navigationState.backStacks[navigationState.topLevelDestination]?.last()==Notes || navigationState.backStacks[navigationState.topLevelDestination]?.last()==Todos)){
                                TopNavBar(Modifier,selectedKey=navigationState.topLevelDestination,onSelectedKey={navigator.navigate(it)})
                            }
                    },
                    floatingActionButton={
                        if(navigationState.backStacks[navigationState.topLevelDestination]?.last() is Notes || navigationState.backStacks[navigationState.topLevelDestination]?.last() is Todos){
                            val interactSource = remember{MutableInteractionSource()}
                            val pressed by interactSource.collectIsPressedAsState()
                            val scale by animateFloatAsState(
                                targetValue = if (pressed) 0.88f else 1f,
                                label="button_scale",
                                animationSpec=spring(stiffness=Spring.StiffnessLow,
                                    dampingRatio=Spring.DampingRatioLowBouncy)
                            )
                            val fabShape = RoundedCornerShape(50.dp)
                            AnimatedContent(targetState=navigationState.topLevelDestination,modifier=Modifier,transitionSpec={scaleIn(animationSpec=tween(durationMillis=300,delayMillis=20,easing=LinearEasing))+fadeIn(animationSpec=tween(durationMillis=300,delayMillis=20,easing=LinearEasing)) togetherWith scaleOut(animationSpec=tween(durationMillis=300,delayMillis=20,easing=LinearEasing))+fadeOut(animationSpec=tween(durationMillis=300,delayMillis=20,easing=LinearEasing))}){
                                when(it){
                                    Notes->{
                                    AnimatedVisibility(visible=(navigationState.backStacks[navigationState.topLevelDestination]?.last() is Notes || navigationState.backStacks[navigationState.topLevelDestination]?.last() is Todos),enter=scaleIn(animationSpec=tween(durationMillis=500,easing=LinearOutSlowInEasing,delayMillis=50))+fadeIn(animationSpec=tween(durationMillis=500,delayMillis=50,easing=LinearOutSlowInEasing)),exit=scaleOut(animationSpec=tween(durationMillis=500,easing=LinearOutSlowInEasing,delayMillis=50))+fadeOut(animationSpec=tween(durationMillis=500,easing=LinearOutSlowInEasing,delayMillis=50))) {
                                        FloatingActionButton(modifier=Modifier.clickable(interactionSource=interactSource,indication=null,onClick={}).scale(scale).dropShadow(shape=RoundedCornerShape(50.dp),shadow=Shadow(radius=4.dp,spread=1.dp,color=Color(0xFFFFC107))).border(1.dp,MaterialTheme.colorScheme.onSurface,fabShape),
                                            onClick={
                                                if(navigationState.topLevelDestination==Notes){
                                                    navigationState.backStacks[navigationState.topLevelDestination]?.add(Note)
                                                }else if(navigationState.topLevelDestination==Todos){
                                                }
                                            },
                                            shape=fabShape,
                                            containerColor= fabColor,
                                            contentColor=MaterialTheme.colorScheme.onSurface,
                                            interactionSource=interactSource){
                                            Icon(Icons.Filled.Add,contentDescription="Add",modifier=Modifier.size(43.dp))
                                        }
                                    }}
                                    Todos-> {
                                        AnimatedVisibility(
                                            visible = (navigationState.backStacks[navigationState.topLevelDestination]?.last() is Notes || navigationState.backStacks[navigationState.topLevelDestination]?.last() is Todos),
                                            enter = scaleIn(
                                                animationSpec = tween(
                                                    durationMillis = 500,
                                                    easing = LinearOutSlowInEasing,
                                                    delayMillis = 50
                                                )
                                            ) + fadeIn(
                                                animationSpec = tween(
                                                    durationMillis = 500,
                                                    delayMillis = 50,
                                                    easing = LinearOutSlowInEasing
                                                )
                                            ),
                                            exit = scaleOut(
                                                animationSpec = tween(
                                                    durationMillis = 500,
                                                    easing = LinearOutSlowInEasing,
                                                    delayMillis = 50
                                                )
                                            ) + fadeOut(
                                                animationSpec = tween(
                                                    durationMillis = 500,
                                                    easing = LinearOutSlowInEasing,
                                                    delayMillis = 50
                                                )
                                            )
                                        ) {
                                            FloatingActionButton(
                                                modifier = Modifier.clickable(
                                                    interactionSource = interactSource,
                                                    indication = null,
                                                    onClick = {}).scale(scale).dropShadow(
                                                    shape = RoundedCornerShape(50.dp),
                                                    shadow = Shadow(
                                                        radius = 4.dp,
                                                        spread = 1.dp,
                                                        color = Color(0xFFFFC107)
                                                    )
                                                ).border(
                                                    1.dp,
                                                    MaterialTheme.colorScheme.onSurface,
                                                    fabShape
                                                ),
                                                onClick = {
                                                    if (navigationState.topLevelDestination == Notes) {
                                                        navigationState.backStacks[navigationState.topLevelDestination]?.add(
                                                            Note
                                                        )
                                                    } else if (navigationState.topLevelDestination == Todos) {
                                                    }
                                                },
                                                shape = fabShape,
                                                containerColor = fabColor,
                                                contentColor = MaterialTheme.colorScheme.onSurface,
                                                interactionSource = interactSource
                                            ) {
                                                Icon(
                                                    Icons.Filled.Add,
                                                    contentDescription = "Add",
                                                    modifier = Modifier.size(43.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    },
                    floatingActionButtonPosition=FabPosition.End){innerPadding->
                    val currentBackStack=navigationState.backStacks[navigationState.topLevelDestination]?:navigationState.backStacks.getValue(Notes)
                    NavDisplay(
                        entries = navigationState.toEntries(entryProvider {
                            entry<Notes> {
                                NoteScreen(currentBackStack,noteViewModel,navigationState.topLevelDestination== Notes)
                            }
                            entry<Todos> {
                                TodoScreen(currentBackStack,todoViewModel,navigationState.topLevelDestination==Todos)
                            }
                            entry<Note>(metadata=NavDisplay.transitionSpec{
                                slideInHorizontally(animationSpec=tween(durationMillis=120,easing=LinearOutSlowInEasing,delayMillis=100)){it} + fadeIn(animationSpec=tween(durationMillis=120,delayMillis=100,easing=LinearOutSlowInEasing)) togetherWith slideOutHorizontally(animationSpec=tween(durationMillis=120,easing=LinearOutSlowInEasing,delayMillis=100)){-it} + fadeOut(animationSpec=tween(durationMillis=120,easing=LinearOutSlowInEasing,delayMillis=100))
                            }+NavDisplay.popTransitionSpec{
                                slideInHorizontally(animationSpec=tween(durationMillis=120,easing=LinearOutSlowInEasing,delayMillis=100)){-it} + fadeIn(animationSpec=tween(durationMillis=120,delayMillis=100,easing=LinearOutSlowInEasing)) togetherWith slideOutHorizontally(animationSpec=tween(durationMillis=120,easing=LinearOutSlowInEasing,delayMillis=100)){it} + fadeOut(animationSpec=tween(durationMillis=120,easing=LinearOutSlowInEasing,delayMillis=100))
                            }+NavDisplay.predictivePopTransitionSpec{
                                slideInHorizontally(animationSpec=tween(durationMillis=120,easing=LinearOutSlowInEasing,delayMillis=100)){-it} + fadeIn(animationSpec=tween(durationMillis=120,delayMillis=100,easing=LinearOutSlowInEasing)) togetherWith slideOutHorizontally(animationSpec=tween(durationMillis=120,easing=LinearOutSlowInEasing,delayMillis=100)){it} + fadeOut(animationSpec=tween(durationMillis=120,easing=LinearOutSlowInEasing,delayMillis=100))
                            }) {
                                Note(currentBackStack,noteViewModel,currentBackStack.last()==Note)
                            }
                            entry<Search>(metadata=NavDisplay.transitionSpec{
                                slideIn(animationSpec=tween(durationMillis=120,easing=CubicBezierEasing(0.0f,0f,0.2f,1f))){fullSize-> IntOffset(fullSize.width-5,-(fullSize.height))} + fadeIn(animationSpec = tween(durationMillis = 120))  togetherWith slideOut(animationSpec = tween(durationMillis = 120, easing = CubicBezierEasing(0.0f, 0f, 0.2f, 1f))){fullSize->IntOffset(fullSize.width-5,-(fullSize.height-5))} + fadeOut(animationSpec = tween(durationMillis = 120))
                            }+ NavDisplay.popTransitionSpec{
                                slideInHorizontally(animationSpec=tween(durationMillis=120,easing=LinearOutSlowInEasing,delayMillis=100)){-it} + fadeIn(animationSpec=tween(durationMillis=120,delayMillis=100,easing=LinearOutSlowInEasing)) togetherWith slideOutHorizontally(animationSpec=tween(durationMillis=120,easing=LinearOutSlowInEasing,delayMillis=100)){it} + fadeOut(animationSpec=tween(durationMillis=120,easing=LinearOutSlowInEasing,delayMillis=100))
                            }+ NavDisplay.predictivePopTransitionSpec{
                                slideInHorizontally(animationSpec=tween(durationMillis=120,easing=LinearOutSlowInEasing,delayMillis=100)){-it} + fadeIn(animationSpec=tween(durationMillis=120,delayMillis=100,easing=LinearOutSlowInEasing)) togetherWith slideOutHorizontally(animationSpec=tween(durationMillis=120,easing=LinearOutSlowInEasing,delayMillis=100)){it} + fadeOut(animationSpec=tween(durationMillis=120,delayMillis=100,easing=LinearOutSlowInEasing))
                            }) {
                                Search(currentBackStack,noteViewModel)
                            }
                        }),
                        transitionSpec={
                            val current = initialState.key.toString()
                            val target = targetState.key.toString()
                            val currentIndex=when{
                                current.contains("Notes")->0
                                current.contains("Todos")->1
                                else -> -1
                            }
                            val targetIndex=when{
                                target.contains("Notes")->0
                                target.contains("Todos")->1
                                else -> -1
                            }
                            if(currentIndex!=-1 && targetIndex!=-1 && currentIndex>targetIndex){
                                slideInHorizontally(animationSpec=tween(durationMillis=120,easing=LinearOutSlowInEasing,delayMillis=250),initialOffsetX={width->-(width)})+fadeIn(animationSpec=tween(durationMillis=120,easing=LinearOutSlowInEasing,delayMillis=250)) togetherWith slideOutHorizontally(animationSpec=tween(durationMillis=120,easing=LinearOutSlowInEasing,delayMillis=250), targetOffsetX = {width->(width)})+fadeOut(animationSpec=tween(durationMillis=120,easing=LinearOutSlowInEasing,delayMillis=250))
                            }else{
                                slideInHorizontally(animationSpec=tween(durationMillis=120,easing=LinearOutSlowInEasing,delayMillis=250),initialOffsetX={width->width})+fadeIn(animationSpec=tween(durationMillis=120,easing=LinearOutSlowInEasing,delayMillis=250)) togetherWith slideOutHorizontally(animationSpec=tween(durationMillis=120,easing=LinearOutSlowInEasing,delayMillis=250), targetOffsetX ={width->-(width)})+fadeOut(animationSpec=tween(durationMillis=120,easing=LinearOutSlowInEasing,delayMillis=250))
                            }
                        },
                        popTransitionSpec={
                            val current = initialState.key.toString()
                            val target = targetState.key.toString()
                            val currentIndex=when{
                                current.contains("navigation.Notes") || current=="Notes"->0
                                current.contains("navigation.Todos") || current=="Todos"->1
                                else -> -1
                            }
                            val targetIndex=when{
                                target.contains("navigation.Notes") || target=="Notes" ->0
                                target.contains("navigation.Todos") || target=="Todos"->1
                                else -> -1
                            }
                            if(currentIndex!=-1 && targetIndex!=-1 && currentIndex<targetIndex){
                                slideInHorizontally(animationSpec=tween(durationMillis=120,easing=LinearOutSlowInEasing,delayMillis=250),initialOffsetX={it})+fadeIn(animationSpec=tween(durationMillis=120,easing=LinearOutSlowInEasing,delayMillis=250)) togetherWith slideOutHorizontally(animationSpec=tween(durationMillis=120,easing=LinearOutSlowInEasing,delayMillis=250), targetOffsetX = {-it})+fadeOut(animationSpec=tween(durationMillis=120,easing=LinearOutSlowInEasing,delayMillis=250))
                            }else{
                                slideInHorizontally(animationSpec=tween(durationMillis=120,easing=LinearOutSlowInEasing,delayMillis=250),initialOffsetX={-it})+fadeIn(animationSpec=tween(durationMillis=120,easing=LinearOutSlowInEasing,delayMillis=250)) togetherWith slideOutHorizontally(animationSpec=tween(durationMillis=120,easing=LinearOutSlowInEasing,delayMillis=250), targetOffsetX ={it})+fadeOut(animationSpec=tween(durationMillis=120,easing=LinearOutSlowInEasing,delayMillis=250))
                            }
                        },
                        predictivePopTransitionSpec={
                            val current = initialState.key.toString()
                            val target = targetState.key.toString()
                            val currentIndex=when{
                                current.contains("navigation.Notes") || current=="Notes"->0
                                current.contains("navigation.Todos") || current=="Todos"->1
                                else -> -1
                            }
                            val targetIndex=when{
                                target.contains("navigation.Notes") || target=="Notes" ->0
                                target.contains("navigation.Todos") || target=="Todos"->1
                                else -> -1
                            }
                            if(currentIndex!=-1 && targetIndex!=-1 && currentIndex<targetIndex){
                                slideInHorizontally(animationSpec=tween(durationMillis=120,easing=LinearOutSlowInEasing,delayMillis=250),initialOffsetX={it})+fadeIn(animationSpec=tween(durationMillis=120,easing=LinearOutSlowInEasing,delayMillis=250)) togetherWith slideOutHorizontally(animationSpec=tween(durationMillis=120,easing=LinearOutSlowInEasing,delayMillis=250), targetOffsetX = {-it})+fadeOut(animationSpec=tween(durationMillis=120,easing=LinearOutSlowInEasing,delayMillis=250))
                            }else{
                                slideInHorizontally(animationSpec=tween(durationMillis=120,easing=LinearOutSlowInEasing,delayMillis=250),initialOffsetX={-it})+fadeIn(animationSpec=tween(durationMillis=120,easing=LinearOutSlowInEasing,delayMillis=250)) togetherWith slideOutHorizontally(animationSpec=tween(durationMillis=120,easing=LinearOutSlowInEasing,delayMillis=250), targetOffsetX ={it})+fadeOut(animationSpec=tween(durationMillis=120,easing=LinearOutSlowInEasing,delayMillis=250))
                            }
                        },
                        onBack = navigator::goBack,
                        modifier = Modifier.padding(bottom=innerPadding.calculateBottomPadding())
                    )
                }
            }
        }
    }
}
