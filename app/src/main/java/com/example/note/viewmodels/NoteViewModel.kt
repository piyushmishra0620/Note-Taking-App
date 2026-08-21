package com.example.note.viewmodels

import androidx.lifecycle.*
import com.example.note.data.repository.NoteRepository
import com.example.note.models.Notes
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


class NoteViewModel(private val noteRepository:NoteRepository):ViewModel() {

}
