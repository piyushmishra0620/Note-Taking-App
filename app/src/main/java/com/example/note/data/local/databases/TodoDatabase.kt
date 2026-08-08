package com.example.note.data.local.databases

import androidx.room.*;
import com.example.note.data.local.dao.TodoDAO;
import com.example.note.data.local.entities.Todos;

@Database(
    entities=[Todos::class],
    version=1
)
abstract class TodoDatabase:RoomDatabase() {
    abstract fun todoDao():TodoDAO;
}
