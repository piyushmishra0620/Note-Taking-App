package com.example.note.data.local.dao

import androidx.room.*;
import com.example.note.data.local.entities.Todos;

@Dao
interface TodoDAO {
    @Insert
    suspend fun insertTodo(todo:Todos)

    @Update
    suspend fun updateTodos(todo:Todos)

    @Delete
    suspend fun deleteTodos(todo:Todos)


}
