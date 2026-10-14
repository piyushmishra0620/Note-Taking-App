package com.example.note

import android.app.Application
import androidx.lifecycle.viewmodel.*
import com.example.note.data.local.databases.AppDatabase
import com.example.note.data.repository.*
import com.example.note.viewmodels.*

class NoteApplication:Application() {
    val db by lazy{
        AppDatabase.getInstance(this)
    }
    val noteRepository by lazy{
        NoteRepository(db.noteDao())
    }
    val todoRepository by lazy{
        TodoRepository(db.todoDao())
    }
    val noteViewModel by lazy{
        viewModelFactory{
            initializer {
                NoteViewModel(noteRepository)
            }
        }
    }
    val todoViewModel by lazy{
        viewModelFactory{
            initializer {
                TodoViewModel(todoRepository)
            }
        }
    }
}
