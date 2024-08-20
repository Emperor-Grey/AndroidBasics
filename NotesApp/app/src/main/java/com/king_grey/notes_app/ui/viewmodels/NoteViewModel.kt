package com.king_grey.notes_app.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.king_grey.notes_app.data.model.Note
import com.king_grey.notes_app.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoteViewModel(
    app: Application, private val noteRepository: NoteRepository
) : AndroidViewModel(app) {

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description.asStateFlow()

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    init {
        viewModelScope.launch {
            noteRepository.notes.collect {
                _notes.value = it
            }
        }
    }

    fun addNote(note: Note) = viewModelScope.launch {
        noteRepository.insertNote(note)
    }

    fun removeNote(note: Note) = viewModelScope.launch {
        noteRepository.deleteNote(note)

        _title.value = ""
        _description.value = ""
    }

    fun searchNote(query: String?) = viewModelScope.launch {
        if (query.isNullOrEmpty()) {
            noteRepository.notes.collect { allNotes ->
                _notes.value = allNotes
            }
        } else {
            noteRepository.searchNotes(query).collect { searchedNote ->
                _notes.value = searchedNote
            }
        }
    }

//    suspend fun getSuggestions(query: String): List<String> {
//        return noteRepository.getExistingTitles("%$query%")
//    }


    fun updateNote(note: Note) = viewModelScope.launch {
        noteRepository.updateNote(note)

        _title.value = ""
        _description.value = ""
    }

    fun clearAll() = viewModelScope.launch {
        noteRepository.deleteAllNotes()
    }
}
