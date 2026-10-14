package com.example.note.navigation

import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.compose.foundation.*
import androidx.compose.foundation.border.*
import androidx.compose.foundation.text.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavDestination(
    val label: String,
    val icon: ImageVector,
    val key: NavKey
)

val topLevelDestinations = listOf(
    BottomNavDestination(
        label="Notes",
        icon=Icons.Filled.EditNote,
        key= Notes
    ),
    BottomNavDestination(
        label="To-dos",
        icon=Icons.Filled.Task,
        key= Todos
    )
)

@Composable
fun TopNavBar(
    modifier: Modifier = Modifier,
    selectedKey: NavKey,
    onSelectedKey: (NavKey) -> Unit
) {
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
                    radius=5.dp,
                    spread=1.dp,
                    color=Color.Black,
                    offset=DpOffset(3.dp,3.dp)
                )
            ),
        contentColor = MaterialTheme.colorScheme.onSurface,
        containerColor = MaterialTheme.colorScheme.onPrimary
    ) {
        topLevelDestinations.forEach {
            NavigationBarItem(
                selected = selectedKey == it.key,
                onClick = { onSelectedKey(it.key) },
                colors=NavigationBarItemDefaults.colors(
                    indicatorColor=Color(0xAEFCB203),
                    selectedIconColor=MaterialTheme.colorScheme.onSurface,
                    unselectedIconColor=MaterialTheme.colorScheme.onSurface,
                    selectedTextColor=MaterialTheme.colorScheme.onSurface,
                    unselectedTextColor=MaterialTheme.colorScheme.onSurface
                ),
                icon = {
                    Icon(it.icon, contentDescription = it.label)
                },
                label = {
                    Text(it.label, fontSize = 12.sp, fontWeight = FontWeight.Normal)
                }
            )
        }
    }
}
