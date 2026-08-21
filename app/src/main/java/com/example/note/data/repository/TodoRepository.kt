package com.example.note.data.repository

import com.example.note.data.local.entities.Todos
import com.example.note.data.local.dao.TodoDAO
import kotlinx.coroutines.flow.*
import androidx.paging.*

interface TodoRepositoryInterface{
    suspend fun insertTodo(todo:Todos)
    suspend fun updateTodo(todo:Todos)
    suspend fun deleteTodos(todos:List<Todos>)
    fun retrieveTodosInChunks():Flow<PagingData<Todos>>
}

class TodoRepository(private val todoDao:TodoDAO): TodoRepositoryInterface {
    override suspend fun insertTodo(todo:Todos){
        todoDao.insertTodo(todo)
    }
    override suspend fun updateTodo(todo:Todos){
        todoDao.updateTodos(todo)
    }
    override suspend fun deleteTodos(todo:List<Todos>){
        todoDao.deleteTodos(todo)
    }
    override fun retrieveTodosInChunks():Flow<PagingData<Todos>>{
        return Pager<Int,Todos>(
            config=PagingConfig(
                pageSize=10
            ),
            pagingSourceFactory={todoDao.retrieveTodosInChunks()}
        ).flow
    }
}
