package com.example.note.screens

import androidx.compose.runtime.*;
import androidx.compose.foundation.*;
import androidx.compose.ui.unit.*;
import androidx.compose.ui.*;
import androidx.compose.ui.tooling.preview.Preview;
import androidx.compose.foundation.shape.*;
import androidx.compose.foundation.border.*;
import androidx.compose.ui.graphics.*;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.*;
import androidx.compose.material3.*;
import androidx.compose.ui.text.*;
import androidx.compose.ui.text.font.*;
import androidx.compose.material.icons.filled.*;
import androidx.compose.foundation.interaction.*;
import androidx.compose.animation.*;
import androidx.compose.animation.core.*;
import androidx.compose.ui.draw.*;
import androidx.compose.ui.graphics.shadow.Shadow;
import com.example.note.viewmodels.TodoViewModel
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(todoViewModel:TodoViewModel=viewModel()){
    Scaffold(
        topBar={
            TopAppBar(
                title={Text("To-dos",fontWeight=FontWeight.Bold,fontSize=25.sp)},
                actions={
                },
                colors=TopAppBarDefaults.topAppBarColors(containerColor=Color.Transparent,titleContentColor=MaterialTheme.colorScheme.onSurface,actionIconContentColor=MaterialTheme.colorScheme.onSurface),
                scrollBehavior=TopAppBarDefaults.pinnedScrollBehavior()
            )
        },
        floatingActionButton={
            val interactSource = remember { MutableInteractionSource() }
            val isPressed by interactSource.collectIsPressedAsState();
            val scale by animateFloatAsState(
                targetValue = if(isPressed) 0.92f else 1.0f,
                label="animated_button",
                animationSpec=spring(stiffness=Spring.StiffnessLow,
                    dampingRatio=Spring.DampingRatioLowBouncy)
            )
            FloatingActionButton(
                modifier=Modifier.clickable(interactionSource=interactSource,indication=null,onClick={}).scale(scale).dropShadow(shape=RoundedCornerShape(50.dp),shadow=Shadow(radius=4.dp,spread=1.dp,color=Color(0xFFFFC107))).border(1.dp,MaterialTheme.colorScheme.onSurface,RoundedCornerShape(50.dp)),
                onClick={},
                contentColor=MaterialTheme.colorScheme.onSurface,
                shape=RoundedCornerShape(50.dp),
                interactionSource=interactSource
            ){
                Icon(Icons.Filled.Add,contentDescription="",modifier=Modifier.size(32.dp))
            }
        }
    ){
        innerPadding->
        Modifier.padding(innerPadding)
    }
}

@Preview(showBackground=true)
@Composable
fun TodoScreenPreview(){
    TodoScreen(viewModel())
}
