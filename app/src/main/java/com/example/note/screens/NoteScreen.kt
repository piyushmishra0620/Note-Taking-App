package com.example.note.screens

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.graphics.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.shape.*
import androidx.compose.animation.core.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.navigation3.runtime.*
import com.example.note.navigation.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(backStack: NavBackStack<NavKey>){
    Scaffold(modifier=Modifier.fillMaxSize(),topBar={
        TopAppBar(title={
            Text(text="Notes",fontWeight=FontWeight.Bold,fontSize=25.sp)
        },actions={
            var expanded by rememberSaveable{
                mutableStateOf(false)
            }
            var view by rememberSaveable{
                mutableStateOf("List View")
            }
            var menuText by remember{
                mutableStateOf(view)
            }
            LaunchedEffect(expanded){
                if(expanded){
                    menuText=view
                }
            }
            IconButton(onClick={
                backStack.add(Search)
            }){
                Icon(Icons.Filled.Search,contentDescription="Search",modifier=Modifier.size(32.dp))
            }
            IconButton(onClick={
                expanded=!(expanded)
            }){
                Icon(Icons.Filled.MoreVert,contentDescription="",modifier=Modifier.size(32.dp))
            }
            DropdownMenu(
                expanded=expanded,
                onDismissRequest={expanded=false},
                offset=DpOffset(x=(-12).dp,y=0.dp),
                modifier=Modifier.padding(end=6.dp,start=6.dp),
                shadowElevation=6.dp,
                border=BorderStroke(3.dp,MaterialTheme.colorScheme.onTertiary),
                shape=RoundedCornerShape(10.dp),
                containerColor=MaterialTheme.colorScheme.background
            ){
                DropdownMenuItem(text={Text("Edit",color=MaterialTheme.colorScheme.onSurface,fontSize=16.sp,fontWeight=FontWeight.SemiBold)},onClick={expanded=false})
                HorizontalDivider(thickness=Dp.Hairline,color=MaterialTheme.colorScheme.onTertiary)
                DropdownMenuItem(text={Text(menuText,color=MaterialTheme.colorScheme.onSurface,fontSize=16.sp,fontWeight=FontWeight.SemiBold)},onClick={
                    expanded=false
                    view = if(view=="List View") "Grid View" else "List View"
                })
                HorizontalDivider(thickness=Dp.Hairline,color=MaterialTheme.colorScheme.onTertiary)
                DropdownMenuItem(text={Text("Sort by time created",color=MaterialTheme.colorScheme.onSurface,fontSize=16.sp,fontWeight=FontWeight.SemiBold)},onClick={expanded=false})
            }
        },
            colors=TopAppBarDefaults.topAppBarColors(containerColor=Color.Transparent,titleContentColor=MaterialTheme.colorScheme.onSurface,actionIconContentColor=MaterialTheme.colorScheme.onSurface),
            scrollBehavior=TopAppBarDefaults.pinnedScrollBehavior())
    },
        floatingActionButton={
            val interactSource = remember{MutableInteractionSource()}
            val pressed by interactSource.collectIsPressedAsState()
            val scale by animateFloatAsState(
                targetValue = if (pressed) 0.92f else 1f,
                label="button_scale",
                animationSpec=spring(stiffness=Spring.StiffnessLow,
                    dampingRatio=Spring.DampingRatioLowBouncy)
            )
            val fabShape = RoundedCornerShape(50.dp)
            FloatingActionButton(modifier=Modifier.clickable(interactionSource=interactSource,indication=null,onClick={}).scale(scale).dropShadow(shape=RoundedCornerShape(50.dp),shadow=Shadow(radius=4.dp,spread=1.dp,color=Color(0xFFFFC107))).border(1.dp,MaterialTheme.colorScheme.onSurface,fabShape),
                onClick={
                    backStack.add(Note)
                },
                shape=fabShape,
                contentColor=MaterialTheme.colorScheme.onSurface,
                interactionSource=interactSource){
                Icon(Icons.Filled.Add,contentDescription="Add",modifier=Modifier.size(43.dp))
            }
        },
        floatingActionButtonPosition=FabPosition.End) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            // Screen content
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground=true)
@Composable
fun NoteScreenPreview(){
    NoteScreen(backStack = rememberNavBackStack(Notes))
}
