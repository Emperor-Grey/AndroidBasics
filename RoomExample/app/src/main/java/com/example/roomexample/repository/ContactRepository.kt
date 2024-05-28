package com.example.roomexample.repository

import com.example.roomexample.data.dao.ContactDao
import com.example.roomexample.data.model.Contact

class ContactRepository(private val contactDao: ContactDao) {

    val contacts = contactDao.getAllContacts()

    suspend fun addContact(contact: Contact) = contactDao.addContact(contact)
    suspend fun updateContact(contact: Contact) = contactDao.updateContact(contact)
    suspend fun removeContact(contact: Contact) = contactDao.removeContact(contact)

    suspend fun getSelectedContact(contact: Contact) = contactDao.getContactById(contact.id)
    suspend fun removeAllContacts() = contactDao.deleteAll()
}
