package com.example.notesapp.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.model.Note
import com.example.notesapp.repository.NoteRepository
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
        noteRepository.searchNotes(query).collect { searchedNote ->
            _notes.value = searchedNote
        }
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        noteRepository.updateNote(note)

        _title.value = ""
        _description.value = ""
    }

    fun clearAll() = viewModelScope.launch {
        noteRepository.deleteAllNotes()
    }

    fun initUpdateOrDelete(note: Note) {
        _title.value = note.title
        _description.value = note.description
    }
}
