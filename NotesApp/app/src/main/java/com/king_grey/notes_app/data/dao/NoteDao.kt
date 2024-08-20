package com.king_grey.notes_app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.king_grey.notes_app.data.model.Note
import com.king_grey.notes_app.utils.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note: Note)

    @Update  // you can use upsert also
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM ${Constants.TABLE_NAME}")
    fun getAllNotes(): Flow<List<Note>>

    @Query("DELETE FROM ${Constants.TABLE_NAME}")
    suspend fun deleteAllNotes()

    @Query("SELECT * FROM ${Constants.TABLE_NAME} WHERE id = :id")
    suspend fun getNoteById(id: Int): Note

    @Query("SELECT * FROM ${Constants.TABLE_NAME} WHERE title LIKE :query OR description LIKE  :query ")
    fun searchNote(query: String?): Flow<List<Note>>
//
//    @Query("SELECT title FROM ${Constants.TABLE_NAME} WHERE title LIKE :query")
//    suspend fun getExistingTitles(query: String): List<String>
}
