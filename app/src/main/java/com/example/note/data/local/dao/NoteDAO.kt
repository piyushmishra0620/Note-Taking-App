package com.example.note.data.local.dao

import androidx.room.*
import com.example.note.data.local.entities.Notes
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDAO {
    @Insert
    suspend fun insertNote(note:Notes)

    @Update
    suspend fun updateNote(note:Notes)

    @Delete
    suspend fun deleteNote(notes:List<Notes>)

    @Query("SELECT * FROM Notes LIMIT 10 OFFSET :offset")
    suspend fun retrieveNotesInChunks(offset:Int): Flow<List<Notes>>
}
