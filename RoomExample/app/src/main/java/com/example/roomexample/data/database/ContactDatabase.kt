package com.example.roomexample.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.roomexample.data.dao.ContactDao
import com.example.roomexample.data.model.Contact
import com.example.roomexample.utils.Constants

@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class ContactDatabase : RoomDatabase() {
    abstract val contactDao: ContactDao

    // Singleton Pattern
    companion object {
        @Volatile // For multiple threaded operations edge case
        private var INSTANCE: ContactDatabase? = null

        fun getInstance(context: Context): ContactDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ContactDatabase::class.java,
                        Constants.DATABASE_NAME,
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
