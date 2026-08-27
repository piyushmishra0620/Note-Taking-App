package com.example.note

import android.os.Bundle
import android.view.animation.*
import androidx.activity.*
import androidx.activity.compose.*
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
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

class MainActivity : ComponentActivity() {

    private val app by lazy { application as NoteApplication }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashscreen = installSplashScreen()
        splashscreen.setOnExitAnimationListener { splashScreenView ->
            splashScreenView.view.animate().setStartDelay(20).setDuration(80).alpha(0f).scaleX(0f)
                .scaleY(0f)
                .setInterpolator(DecelerateInterpolator())
                .withEndAction { splashScreenView.remove() }.start()
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val noteViewModel : NoteViewModel = viewModel(factory=app.noteViewModel)
            val todoViewModel:TodoViewModel = viewModel(factory=app.todoViewModel)
            val backStack = rememberNavBackStack(Notes)
            val currentScreen = backStack.lastOrNull()
            NoteTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (currentScreen is Notes || currentScreen is Todos) {
                            BottomAppBar(
                                modifier = Modifier
                                    .height(115.dp)
                                    .padding(bottom = 15.dp, start = 15.dp, end = 15.dp, top = 8.dp)
                                    .navigationBarsPadding()
                                    .clip(RoundedCornerShape(20.dp))
                                    .border(
                                        2.dp,
                                        MaterialTheme.colorScheme.onSecondary,
                                        shape = RoundedCornerShape(20.dp)
                                    )
                                    .dropShadow(
                                        shape = RoundedCornerShape(20.dp),
                                        shadow = Shadow(
                                            radius = 5.dp,
                                            spread = 1.dp,
                                            color = Color.Black,
                                            offset = DpOffset(x = 3.dp, y = 3.dp)
                                        )
                                    ),
                                actions = {
                                    Spacer(Modifier.weight(0.25f))
                                    IconButton(onClick = {
                                        if (backStack.lastOrNull() != Notes) {
                                            backStack.add(Notes)
                                        }
                                    }, Modifier.size(50.dp)) {
                                        Column(
                                            Modifier,
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Icon(
                                                Icons.Filled.EditNote,
                                                contentDescription = "Notes",
                                                modifier = Modifier.size(28.dp)
                                            )
                                            Text(
                                                text = "Notes",
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Normal
                                            )
                                        }
                                    }
                                    Spacer(Modifier.weight(0.5f))
                                    IconButton(onClick = {
                                        if (backStack.lastOrNull() != Todos) {
                                            backStack.add(Todos)
                                        }
                                    }, Modifier.size(53.dp)) {
                                        Column(
                                            Modifier,
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Icon(
                                                Icons.Filled.Task,
                                                contentDescription = "Todo",
                                                modifier = Modifier.size(28.dp)
                                            )
                                            Text(
                                                text = "To-dos",
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Normal
                                            )
                                        }
                                    }
                                    Spacer(Modifier.weight(0.25f))
                                },
                                contentColor = MaterialTheme.colorScheme.onSurface,
                                containerColor = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }) {innerPadding->
                    NavDisplay(
                        backStack = backStack,
                        onBack = {
                            backStack.removeLastOrNull()
                        },
                        entryProvider = entryProvider {
                            entry<Notes> {
                                NoteScreen(backStack,noteViewModel)
                            }
                            entry<Todos> {
                                TodoScreen(todoViewModel)
                            }
                            entry<Note> {
                                Note(backStack,noteViewModel)
                            }
                            entry<Search> {
                                Search()
                            }
                        },
                        transitionSpec={
                            slideInHorizontally { it } + fadeIn() togetherWith
                                    slideOutHorizontally {-it} + fadeOut()
                        },
                        popTransitionSpec={
                            slideInHorizontally {-it} + fadeIn() togetherWith
                                    slideOutHorizontally {it} + fadeOut()
                        },
                        predictivePopTransitionSpec={
                            slideInHorizontally {-it} + fadeIn() togetherWith
                                    slideOutHorizontally {it} + fadeOut()
                        },
                        modifier = Modifier.padding(bottom=innerPadding.calculateBottomPadding())
                    )
                }
            }
        }
    }
}
