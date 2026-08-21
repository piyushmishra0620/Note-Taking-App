package com.example.note.data.repository

import com.example.note.data.local.entities.Todos
import com.example.note.data.local.dao.TodoDAO
import kotlinx.coroutines.flow.*

class TodoRepository(private val todoDao:TodoDAO) {
    suspend fun insertTodo(todo:Todos){
        todoDao.insertTodo(todo)
    }
    suspend fun updateTodo(todo:Todos){
        todoDao.updateTodos(todo)
    }
    suspend fun deleteTodo(todo:List<Todos>){
        todoDao.deleteTodos(todo)
    }
    suspend fun retrieveTodosInChunks(offset:Int):Flow<List<Todos>>{
        return todoDao.retrieveTodosInChunks(offset)
    }
}
