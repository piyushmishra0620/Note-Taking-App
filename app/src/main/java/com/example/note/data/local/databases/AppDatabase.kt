package com.example.note.data.local.databases

import android.content.Context
import androidx.room.*;
import com.example.note.data.local.dao.*;
import com.example.note.data.local.entities.Notes;
import com.example.note.data.local.entities.Todos

@Database(
    entities=[Notes::class,Todos::class],
    version=1
)
abstract class AppDatabase:RoomDatabase() {
    abstract fun noteDao():NoteDAO;
    abstract fun todoDao():TodoDAO;
    companion object{
        @Volatile
        private var INSTANCE:AppDatabase?=null;
        fun getInstance(context:Context):AppDatabase{
            return INSTANCE?:synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE=instance
                instance
            }
        }
    }
}
