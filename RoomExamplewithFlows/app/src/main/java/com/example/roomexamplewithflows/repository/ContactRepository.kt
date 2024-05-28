package com.example.roomexamplewithflows.repository

import com.example.roomexamplewithflows.data.dao.ContactDao
import com.example.roomexamplewithflows.data.model.Contact

class ContactRepository(private val contactDao: ContactDao) {

    val contacts = contactDao.getAllContacts()

    suspend fun insertContact(contact: Contact) = contactDao.addContact(contact)
    suspend fun updateContact(contact: Contact) = contactDao.updateContact(contact)
    suspend fun removeContact(contact: Contact) = contactDao.deleteContact(contact)
    suspend fun getSelectedContact(contact: Contact) = contactDao.getContactById(contact.id)
    suspend fun deleteAll() = contactDao.deleteAllContacts()

}
