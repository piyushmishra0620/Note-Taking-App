package com.example.note.data.local.databases

import androidx.room.*;
import com.example.note.data.local.dao.NoteDAO;
import com.example.note.data.local.entities.Notes;

@Database(
    entities=[Notes::class],
    version=1
)
abstract class NoteDatabase:RoomDatabase() {
    abstract fun noteDao():NoteDAO;
}
