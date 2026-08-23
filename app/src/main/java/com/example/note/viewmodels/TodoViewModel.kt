package com.example.note.viewmodels

import com.example.note.data.repository.TodoRepository
import com.example.note.data.local.entities.Todos
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import androidx.paging.*

class TodoViewModel(private val todoRepository:TodoRepository):ViewModel() {

    val todos:Flow<PagingData<Todos>> = todoRepository.retrieveTodosInChunks().cachedIn(viewModelScope)

    fun insertTodo(todo:Todos){
        viewModelScope.launch{
            todoRepository.insertTodo(todo)
        }
    }

    fun updateTodo(todo:Todos){
        viewModelScope.launch{
            todoRepository.updateTodo(todo)
        }
    }

    fun deleteTodos(todos:List<Todos>){
        viewModelScope.launch{
            todoRepository.deleteTodos(todos)
        }
    }
}
