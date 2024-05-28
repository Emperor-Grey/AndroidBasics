package com.example.roomexamplewithflows.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.roomexamplewithflows.data.model.Contact
import com.example.roomexamplewithflows.utils.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addContact(contact: Contact)

    @Update
    suspend fun updateContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)

    @Query("SELECT * FROM ${Constants.TABLE_NAME}")
    fun getAllContacts(): Flow<List<Contact>>

    @Query("DELETE FROM ${Constants.TABLE_NAME}")
    suspend fun deleteAllContacts()

    @Query("SELECT * FROM ${Constants.TABLE_NAME} WHERE id = :id")
    suspend fun getContactById(id: Int): Contact
}
