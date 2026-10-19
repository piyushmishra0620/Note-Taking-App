package com.example.note.screens

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.border.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*;
import androidx.compose.ui.text.font.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.ui.graphics.*;
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview;
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.*
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.note.viewmodels.NoteViewModel
import androidx.navigation3.*
import androidx.navigation3.runtime.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Note(navBackStack:NavBackStack<NavKey>,viewModel:NoteViewModel= viewModel(),isSelected:Boolean) {
    Scaffold(
        modifier = Modifier.fillMaxSize(), topBar = {
            var expanded by rememberSaveable {
                mutableStateOf(false)
            }
            AnimatedVisibility(visible=isSelected,enter=fadeIn(animationSpec=tween(durationMillis=300,easing=LinearOutSlowInEasing,delayMillis=100)),exit=fadeOut(animationSpec=tween(durationMillis=300,easing=LinearOutSlowInEasing,delayMillis=100))){
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(onClick={
                            navBackStack.removeLastOrNull()
                        }){
                            Icon(Icons.AutoMirrored.Filled.ArrowBack,contentDescription="",modifier=Modifier.size(28.dp))
                        }
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(Icons.Filled.Share, contentDescription = "",modifier=Modifier.size(28.dp))
                        }
                        IconButton(onClick = { expanded = !(expanded) }) {
                            Icon(Icons.Filled.MoreVert, contentDescription = "",modifier=Modifier.size(28.dp))
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            offset = DpOffset(x = (-12).dp, y = 0.dp),
                            modifier = Modifier.padding(end = 6.dp, start = 6.dp).size(180.dp,163.dp),
                            shadowElevation = 6.dp,
                            border = BorderStroke(3.dp, MaterialTheme.colorScheme.onTertiary),
                            shape = RoundedCornerShape(10.dp),
                            containerColor = MaterialTheme.colorScheme.background
                        ) {
                            DropdownMenuItem(text = {
                                Text(
                                    "Find",
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }, onClick = { expanded = false })
                            HorizontalDivider(
                                thickness = 5.dp,
                                color = MaterialTheme.colorScheme.onTertiary
                            )
                            DropdownMenuItem(text = {
                                Text(
                                    "Pin",
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }, onClick = { expanded = false })
                            HorizontalDivider(
                                thickness =5.dp,
                                color = MaterialTheme.colorScheme.onTertiary
                            )
                            DropdownMenuItem(text = {
                                Text(
                                    "Delete",
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }, onClick = { expanded = false })
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                        actionIconContentColor = MaterialTheme.colorScheme.onSurface
                    ), scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
                )
            }
        },
        bottomBar = {
            AnimatedVisibility(visible=isSelected,modifier=Modifier,enter=fadeIn(animationSpec=tween(durationMillis=300,delayMillis=0,easing=LinearOutSlowInEasing)),exit=fadeOut(animationSpec=tween(durationMillis=100,delayMillis=0,easing=LinearOutSlowInEasing))){
                BottomAppBar(
                    modifier=Modifier.height(115.dp).padding(bottom=15.dp,start=55.dp,end=55.dp,top=25.dp).navigationBarsPadding().clip(RoundedCornerShape(40.dp)).border(1.dp,MaterialTheme.colorScheme.onSecondary,shape=RoundedCornerShape(40.dp)).dropShadow(shape= RoundedCornerShape(20.dp),shadow=Shadow(radius=5.dp,spread=1.dp,color=Color.Black,offset=DpOffset(x=3.dp,y=3.dp))),
                    actions={
                        Row(verticalAlignment=Alignment.CenterVertically){
                            Spacer(modifier=Modifier.weight(0.1f))
                            IconButton(onClick={}){
                                Icon(Icons.Filled.Edit,contentDescription="Edit Note",modifier=Modifier.size(24.dp))
                            }
                            Spacer(modifier=Modifier.weight(0.1f))
                            IconButton(onClick={}){
                                Icon(Icons.Filled.KeyboardVoice,contentDescription="voice",modifier=Modifier.size(24.dp))
                            }
                            Spacer(modifier=Modifier.weight(0.1f))
                            IconButton(onClick={}){
                                Icon(Icons.Filled.ControlPoint,contentDescription="visuals",modifier=Modifier.size(24.dp))
                            }
                            Spacer(modifier=Modifier.weight(0.1f))
                        }
                    },
                    contentColor=MaterialTheme.colorScheme.onSurface,
                    containerColor=MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        floatingActionButton = {}
    ) { paddingValue ->
        Modifier.padding(paddingValue)
    }
}

@Preview(showBackground = true)
@Composable
fun NotePreview() {
    Note(rememberNavBackStack(),viewModel(),false);
}
