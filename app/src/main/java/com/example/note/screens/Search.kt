package com.example.note.screens

import androidx.compose.runtime.*;
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.border.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.modifier.*
import androidx.compose.ui.unit.*
import androidx.navigation3.runtime.*
import androidx.navigation3.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.note.data.local.entities.Notes
import com.example.note.viewmodels.*
import com.example.note.navigation.*

@Composable
fun Search(stack:NavBackStack<NavKey>,viewModel:NoteViewModel=viewModel()){}

@Preview
@Composable
fun SearchPreview(){
    Search(rememberNavBackStack(Notes),viewModel())
}
