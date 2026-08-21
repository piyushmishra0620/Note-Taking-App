package com.example.note.data.repository

import com.example.note.data.local.dao.NoteDAO
import com.example.note.data.local.entities.Notes
import kotlinx.coroutines.flow.*

class NoteRepository(private val noteDao: NoteDAO) {
    suspend fun insertNote(note:Notes){
        noteDao.insertNote(note)
    }
    suspend fun updateNote(note:Notes){
        noteDao.updateNote(note)
    }
    suspend fun deleteNote(note:List<Notes>){
        noteDao.deleteNote(note)
    }
    suspend fun retrieveNotesInChunks(offset:Int):Flow<List<Notes>>{
        return noteDao.retrieveNotesInChunks(offset)
    }
}
