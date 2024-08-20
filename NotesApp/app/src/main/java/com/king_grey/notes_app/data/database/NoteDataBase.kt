package com.king_grey.notes_app.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.king_grey.notes_app.data.dao.NoteDao
import com.king_grey.notes_app.data.model.Note
import com.king_grey.notes_app.utils.Constants

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDataBase : RoomDatabase() {

    abstract fun getNoteDao(): NoteDao

    // singleton pattern
    companion object {
        @Volatile
        private var INSTANCE: NoteDataBase? = null

        fun getInstance(context: Context): NoteDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, NoteDataBase::class.java, Constants.DATABASE_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
