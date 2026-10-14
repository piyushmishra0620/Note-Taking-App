package com.example.note.navigation

import kotlinx.serialization.*;
import androidx.navigation3.runtime.*;

@Serializable
sealed interface AppScreen:NavKey

@Serializable
data object Notes: AppScreen

@Serializable
data object Todos: AppScreen

@Serializable
data object Note: AppScreen

@Serializable
data object Search: AppScreen
