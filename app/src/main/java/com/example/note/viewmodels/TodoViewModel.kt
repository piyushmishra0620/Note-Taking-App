package com.example.note.viewmodels

import com.example.note.data.repository.TodoRepository
import com.example.note.models.Todos
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.*

class TodoViewModel(private val todoRepository:TodoRepository):ViewModel() {
}
