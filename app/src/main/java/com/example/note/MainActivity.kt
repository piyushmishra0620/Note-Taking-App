package com.example.note

import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import androidx.activity.*
import androidx.activity.compose.*
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.note.ui.theme.NoteTheme
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.ui.unit.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.draw.*
import androidx.compose.ui.text.font.FontWeight
import com.example.note.ui.theme.borderStroke
import com.example.note.ui.theme.fabColor
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.foundation.BorderStroke
import androidx.compose.animation.core.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashscreen = installSplashScreen()
        splashscreen.setOnExitAnimationListener{splashScreenView->
                splashScreenView.view.animate().setStartDelay(20).setDuration(80).alpha(0f).translationYBy(-600f)
                    .setInterpolator(DecelerateInterpolator())
                    .withEndAction { splashScreenView.remove() }.start()
            }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent{
            NoteTheme{
                Scaffold(modifier=Modifier.fillMaxSize(),topBar={
                    TopAppBar(title={
                        Text(text="Notes",fontWeight= FontWeight.Bold,fontSize=25.sp)
                    },actions={
                        var expanded by rememberSaveable{
                            mutableStateOf(false)
                        }
                        var view by rememberSaveable{
                            mutableStateOf("List View")
                        }
                        var menuText by remember {
                            mutableStateOf(view)
                        }
                        LaunchedEffect(expanded){
                            if(expanded){
                                menuText=view
                            }
                        }
                        IconButton(onClick={}) {
                            Icon(Icons.Filled.Search,contentDescription="Search",modifier=Modifier.size(32.dp))
                        }
                        IconButton(onClick={expanded=!(expanded)}) {
                            Icon(Icons.Filled.MoreVert,contentDescription="",modifier=Modifier.size(32.dp))
                        }
                        DropdownMenu(
                            expanded=expanded,
                            onDismissRequest={expanded=false},
                            offset = DpOffset(x = (-12).dp, y = 0.dp),
                            modifier = Modifier.padding(end=6.dp,start=6.dp),
                            shadowElevation=6.dp,
                            border=BorderStroke(3.dp, MaterialTheme.colorScheme.onTertiary),
                            shape=RoundedCornerShape(10.dp),
                            containerColor=MaterialTheme.colorScheme.background
                        ){
                            DropdownMenuItem(text={Text("Edit",color=MaterialTheme.colorScheme.onSurface,fontSize=16.sp,fontWeight=FontWeight.SemiBold)},onClick={expanded=false})
                            HorizontalDivider(thickness=Dp.Hairline,color=MaterialTheme.colorScheme.onTertiary)
                            DropdownMenuItem(text={Text(menuText,color=MaterialTheme.colorScheme.onSurface,fontSize=16.sp,fontWeight=FontWeight.SemiBold)},onClick={ expanded=false
                                view= if(view=="List View") "Grid View" else "List View"})
                            HorizontalDivider(thickness=Dp.Hairline,color=MaterialTheme.colorScheme.onTertiary)
                            DropdownMenuItem(text={Text("Sort by time created",color=MaterialTheme.colorScheme.onSurface,fontSize=16.sp,fontWeight=FontWeight.SemiBold)},onClick={expanded=false})
                            HorizontalDivider(thickness=Dp.Hairline,color=MaterialTheme.colorScheme.onTertiary)
                            DropdownMenuItem(text={Text("Sort by time edited",color=MaterialTheme.colorScheme.onSurface,fontSize=16.sp,fontWeight=FontWeight.SemiBold)},onClick={expanded=false})
                        }
                    },
                        colors=TopAppBarDefaults.topAppBarColors(containerColor=Color.Transparent,titleContentColor=MaterialTheme.colorScheme.onSurface,actionIconContentColor=MaterialTheme.colorScheme.onSurface),
                        scrollBehavior=TopAppBarDefaults.pinnedScrollBehavior())
                },
                    bottomBar={
                        BottomAppBar(modifier=Modifier.height(115.dp).padding(bottom=20.dp,start=30.dp,end=30.dp,top=8.dp).navigationBarsPadding().clip(RoundedCornerShape(20.dp)).background(MaterialTheme.colorScheme.onPrimary).border(1.dp,MaterialTheme.colorScheme.onSecondary,shape=RoundedCornerShape(20.dp)).dropShadow(shape=RoundedCornerShape(20.dp),shadow=Shadow(radius=5.dp,spread=1.dp,color=Color.Black,offset=DpOffset(x=3.dp,y=3.dp))),actions={
                            Spacer(Modifier.weight(0.25f))
                            IconButton(onClick={},Modifier.size(50.dp)){
                                Column(Modifier,horizontalAlignment=Alignment.CenterHorizontally,verticalArrangement=Arrangement.Center){
                                    Icon(Icons.Filled.EditNote,contentDescription="Notes",modifier=Modifier.size(28.dp))
                                    Text(text="Notes",fontSize=12.sp,fontWeight=FontWeight.Normal)
                                }
                            }
                            Spacer(Modifier.weight(0.5f))
                            IconButton(onClick={},Modifier.size(53.dp)) {
                                Column(Modifier,horizontalAlignment=Alignment.CenterHorizontally,verticalArrangement=Arrangement.Center){
                                    Icon(Icons.Filled.Task,contentDescription="Todo",modifier=Modifier.size(28.dp))
                                    Text(text="To-dos",fontSize=12.sp,fontWeight=FontWeight.Normal)
                                }
                            }
                            Spacer(Modifier.weight(0.25f))
                        },contentColor=MaterialTheme.colorScheme.onSurface)
                    },
                    floatingActionButton={
                        val interactSource = remember{MutableInteractionSource()}
                        val pressed by interactSource.collectIsPressedAsState()
                        val scale by animateFloatAsState(
                            targetValue= if(pressed) 0.92f else 1f,
                            label="button_scale",
                            animationSpec = spring(
                                stiffness=Spring.StiffnessLow,
                                dampingRatio=Spring.DampingRatioLowBouncy
                            )
                        )
                        val fabShape = RoundedCornerShape(50.dp)
                        FloatingActionButton(
                            onClick={},
                            shape= fabShape,
                            containerColor=fabColor,
                            contentColor=MaterialTheme.colorScheme.onSurface,
                            interactionSource = interactSource,
                            modifier=Modifier.scale(scale)
                                .dropShadow(shape=RoundedCornerShape(50.dp),shadow=Shadow(radius=4.dp,spread=1.dp,color=Color(0xFFFFC107)))
                                .border(1.dp,MaterialTheme.colorScheme.onSurface, fabShape)
                        ){
                            Icon(Icons.Filled.Add,contentDescription="Add",modifier=Modifier.size(43.dp))
                        }
                    },
                    floatingActionButtonPosition=FabPosition.End){
                    innerPadding->Greeting(modifier=Modifier.padding(innerPadding),"Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier:Modifier=Modifier,name:String){
    Text(text="Hello, $name!", modifier = modifier,color=MaterialTheme.colorScheme.onSurface)
}

@Preview(showBackground = true)
@Composable
fun MyScreenPreview(){
    Greeting(name="Android")
}
