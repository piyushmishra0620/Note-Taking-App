package com.example.note.data.local.dao

import androidx.room.*
import com.example.note.data.local.entities.Notes
import androidx.paging.PagingSource

@Dao
interface NoteDAO {
    @Insert
    suspend fun insertNote(note:Notes)

    @Update
    suspend fun updateNote(note:Notes)

    @Delete
    suspend fun deleteNote(notes:List<Notes>)

    @Query("SELECT * FROM Notes")
    fun retrieveNotesInChunks(): PagingSource<Int,Notes>
}
