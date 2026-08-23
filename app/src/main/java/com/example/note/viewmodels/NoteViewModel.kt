package com.example.note.viewmodels

import androidx.lifecycle.*
import com.example.note.data.repository.NoteRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import com.example.note.data.local.entities.Notes
import androidx.paging.*

class NoteViewModel(private val noteRepository:NoteRepository):ViewModel() {

    val notes:Flow<PagingData<Notes>> = noteRepository.retrieveNotesInChunks().cachedIn(viewModelScope)

    fun insertNote(note:Notes){
        viewModelScope.launch{
            noteRepository.insertNote(note)
        }
    }

    fun updateNote(note:Notes){
        viewModelScope.launch{
            noteRepository.updateNote(note)
        }
    }

    fun deleteNotes(notes:List<Notes>){
        viewModelScope.launch{
            noteRepository.deleteNotes(notes)
        }
    }
}
