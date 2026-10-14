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
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.animation.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.*
import com.example.note.navigation.*
import com.example.note.ui.theme.fabColor
import com.example.note.viewmodels.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(backStack: NavBackStack<NavKey>,viewmodel:NoteViewModel=viewModel(),isSelected:Boolean){
    Scaffold(modifier=Modifier.fillMaxSize(),topBar={
        AnimatedVisibility(visible=isSelected,modifier=Modifier,enter=fadeIn(animationSpec=tween(durationMillis=300,easing=LinearOutSlowInEasing,delayMillis=200)),exit=fadeOut(animationSpec=tween(durationMillis=300,delayMillis=200,easing=LinearOutSlowInEasing))){
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
                var sort by rememberSaveable{
                    mutableStateOf("Sort by time created")
                }
                var sortText by remember{
                    mutableStateOf(sort)
                }
                LaunchedEffect(expanded){
                    if(expanded){
                        sortText=sort
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
                    HorizontalDivider(thickness=2.dp,color=MaterialTheme.colorScheme.onTertiary)
                    DropdownMenuItem(text={Text(menuText,color=MaterialTheme.colorScheme.onSurface,fontSize=16.sp,fontWeight=FontWeight.SemiBold)},onClick={
                        expanded=false
                        view = if(view=="List View") "Grid View" else "List View"
                    })
                    HorizontalDivider(thickness=2.dp,color=MaterialTheme.colorScheme.onTertiary)
                    DropdownMenuItem(text={Text(sortText,color=MaterialTheme.colorScheme.onSurface,fontSize=16.sp,fontWeight=FontWeight.SemiBold)},onClick={
                        expanded=false
                        sort = if(sort=="Sort by time created") "Sort by time edited" else "Sort by time created"
                    })
                }
            },
                colors=TopAppBarDefaults.topAppBarColors(containerColor=Color.Transparent,titleContentColor=MaterialTheme.colorScheme.onSurface,actionIconContentColor=MaterialTheme.colorScheme.onSurface),
                scrollBehavior=TopAppBarDefaults.pinnedScrollBehavior())
        }
    }) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            // Screen content
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground=true)
@Composable
fun NoteScreenPreview(){
    NoteScreen(rememberNavBackStack(Notes), viewModel(),true)
}
