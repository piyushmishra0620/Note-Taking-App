package com.example.note.data.local.dao

import androidx.room.*;
import com.example.note.data.local.entities.Todos;
import kotlinx.coroutines.flow.*

@Dao
interface TodoDAO {
    @Insert
    suspend fun insertTodo(todo:Todos)

    @Update
    suspend fun updateTodos(todo:Todos)

    @Delete
    suspend fun deleteTodos(todo:List<Todos>)

    @Query("SELECT * FROM Todos LIMIT 10 OFFSET :offset")
    suspend fun retrieveTodosInChunks(offset:Int):Flow<List<Todos>>
}
