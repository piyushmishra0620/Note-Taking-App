package com.example.note.data.repository

import com.example.note.data.local.dao.NoteDAO
import com.example.note.data.local.entities.Notes
import kotlinx.coroutines.flow.*
import androidx.paging.*

interface NoteRepositoryInterface{
    suspend fun insertNote(note:Notes)
    suspend fun updateNote(note:Notes)
    suspend fun deleteNotes(note:List<Notes>)
    fun retrieveNotesInChunks():Flow<PagingData<Notes>>
}

class NoteRepository(private val noteDao: NoteDAO): NoteRepositoryInterface {
    override suspend fun insertNote(note:Notes){
        noteDao.insertNote(note)
    }
    override suspend fun updateNote(note:Notes){
        noteDao.updateNote(note)
    }
    override suspend fun deleteNotes(note:List<Notes>){
        noteDao.deleteNote(note)
    }
    override fun retrieveNotesInChunks(): Flow<PagingData<Notes>> = Pager(
        config = PagingConfig(
            pageSize = 10,
            prefetchDistance=3,
            initialLoadSize=10
        ),
        pagingSourceFactory = { noteDao.retrieveNotesInChunks() }
    ).flow
}
