package com.example.note.data.local.databases

import android.content.Context
import androidx.room.*;
import com.example.note.data.local.dao.TodoDAO;
import com.example.note.data.local.entities.Todos;

@Database(
    entities=[Todos::class],
    version=1
)
abstract class TodoDatabase:RoomDatabase() {
    abstract fun todoDao():TodoDAO;
    companion object{
        @Volatile
        private var INSTANCE:TodoDatabase?=null;
        fun getInstance(context:Context):TodoDatabase{
            return INSTANCE?:synchronized(this){
                val instance = Room.databaseBuilder(
                    context=context.applicationContext,
                    TodoDatabase::class.java,
                    "todo_database"
                ).build()
                INSTANCE=instance
                instance
            }
        }
    }
}
