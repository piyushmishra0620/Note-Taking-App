package com.example.note.data.local.dao

import androidx.room.*
import com.example.note.data.local.entities.Todos
import androidx.paging.PagingSource

@Dao
interface TodoDAO {
    @Insert
    suspend fun insertTodo(todo:Todos)

    @Update
    suspend fun updateTodos(todo:Todos)

    @Delete
    suspend fun deleteTodos(todo:List<Todos>)

    @Query("SELECT * FROM Todos")
    fun retrieveTodosInChunks():PagingSource<Int,Todos>
}
