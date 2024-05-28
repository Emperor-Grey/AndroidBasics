package com.example.roomexamplewithflows.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.roomexamplewithflows.data.dao.ContactDao
import com.example.roomexamplewithflows.data.model.Contact
import com.example.roomexamplewithflows.utils.Constants

@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class ContactDataBase : RoomDatabase() {
    abstract val contactDao: ContactDao

    companion object {
        @Volatile
        private var INSTANCE: ContactDataBase? = null
        fun getInstance(context: Context): ContactDataBase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ContactDataBase::class.java,
                        Constants.DATABASE_NAME,
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }

        }
    }
}
