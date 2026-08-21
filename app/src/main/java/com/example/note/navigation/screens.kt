package com.example.note.navigation

import kotlinx.serialization.*;
import androidx.navigation3.runtime.*;

@Serializable
data object Notes: NavKey

@Serializable
data object Todos: NavKey

@Serializable
data object Note: NavKey

@Serializable
data object Search: NavKey
