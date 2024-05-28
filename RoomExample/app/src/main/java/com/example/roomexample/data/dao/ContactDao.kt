package com.example.roomexample.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.roomexample.data.model.Contact
import com.example.roomexample.utils.Constants

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addContact(contact: Contact)

    @Update  // you can also use @Upsert
    suspend fun updateContact(contact: Contact)

    @Delete
    suspend fun removeContact(contact: Contact)

    @Query("SELECT * FROM ${Constants.TABLE_NAME}")
    fun getAllContacts(): LiveData<List<Contact>> // you can also use Flows


    @Query("SELECT * FROM ${Constants.TABLE_NAME} WHERE id = :id")
    suspend fun getContactById(id: Int): Contact

    @Query("DELETE FROM ${Constants.TABLE_NAME}")
    suspend fun deleteAll()
}
