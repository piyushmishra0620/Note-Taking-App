package com.example.note.screens

import androidx.compose.runtime.*;
import androidx.compose.material3.*
import androidx.compose.foundation.*;
import androidx.compose.foundation.layout.*;
import androidx.compose.foundation.shape.*;
import androidx.compose.foundation.border.*;
import androidx.compose.ui.*;
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Note() {
    Scaffold(
        modifier = Modifier.fillMaxSize(), topBar = {
            var expanded by rememberSaveable {
                mutableStateOf(false)
            }
            TopAppBar(
                title = {},
                navigationIcon = {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "")
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.Share, contentDescription = "")
                    }
                    IconButton(onClick = { expanded = !(expanded) }) {
                        Icon(Icons.Filled.MoreVert, contentDescription = "")
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        offset = DpOffset(x = (-12).dp, y = 0.dp),
                        modifier = Modifier.padding(end = 6.dp, start = 6.dp),
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
                            thickness = Dp.Hairline,
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
                            thickness = Dp.Hairline,
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
        },
        bottomBar = {
            BottomAppBar(
                modifier=Modifier.height(115.dp).padding(bottom=25.dp,start=20.dp,end=20.dp,top=12.dp).navigationBarsPadding().clip(RoundedCornerShape(40.dp)).border(1.dp,MaterialTheme.colorScheme.onSecondary,shape=RoundedCornerShape(40.dp)).dropShadow(shape= RoundedCornerShape(20.dp),shadow=Shadow(radius=5.dp,spread=1.dp,color=Color.Black,offset=DpOffset(x=3.dp,y=3.dp))),
                actions={
                     Row(verticalAlignment=Alignment.CenterVertically){
                         Spacer(modifier=Modifier.weight(0.15f))
                         IconButton(onClick={}){
                             Icon(Icons.Filled.Edit,contentDescription="Edit Note")
                         }
                         Spacer(modifier=Modifier.weight(0.15f))
                         IconButton(onClick={}){
                             Icon(Icons.Filled.KeyboardVoice,contentDescription="voice")
                         }
                         Spacer(modifier=Modifier.weight(0.15f))
                         IconButton(onClick={}){
                             Icon(Icons.Filled.ControlPoint,contentDescription="visuals")
                         }
                         Spacer(modifier=Modifier.weight(0.15f))
                     }
                },
                contentColor=MaterialTheme.colorScheme.onSurface,
                containerColor=MaterialTheme.colorScheme.onPrimary
            )
        },
        floatingActionButton = {}
    ) { paddingValue ->
        Modifier.padding(paddingValue)
    }
}

@Preview(showBackground = true)
@Composable
fun NotePreview() {
    Note();
}
