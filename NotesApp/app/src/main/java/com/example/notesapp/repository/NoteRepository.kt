package com.example.notesapp.repository

import com.example.notesapp.data.database.NoteDataBase
import com.example.notesapp.data.model.Note

class NoteRepository(
    private val noteDataBase: NoteDataBase
) {
    val notes = noteDataBase.getNoteDao().getAllNotes()

    suspend fun insertNote(note: Note) = noteDataBase.getNoteDao().addNote(note)
    suspend fun deleteNote(note: Note) = noteDataBase.getNoteDao().deleteNote(note)
    suspend fun updateNote(note: Note) = noteDataBase.getNoteDao().updateNote(note)
    suspend fun deleteAllNotes() = noteDataBase.getNoteDao().deleteAllNotes()

//    suspend fun getExistingTitles(query: String): List<String> =
//        noteDataBase.getNoteDao().getExistingTitles(query)

    fun searchNotes(query: String?) = noteDataBase.getNoteDao().searchNote(query)
}
