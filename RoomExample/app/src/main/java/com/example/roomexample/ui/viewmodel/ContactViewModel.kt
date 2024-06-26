package com.example.roomexample.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomexample.data.model.Contact
import com.example.roomexample.repository.ContactRepository
import kotlinx.coroutines.launch

class ContactViewModel(private val contactRepository: ContactRepository) : ViewModel() {

    private var isUpdateOrDelete = false
    private var contactToUpdateOrDelete: Contact? = null

    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()
    val saveOrUpdateButtonTxt = MutableLiveData<String>()
    val clearAll = MutableLiveData<String>()

    init {
        saveOrUpdateButtonTxt.value = "Save"
        clearAll.value = "Clear All"
    }

    private val _allContacts = contactRepository.contacts
    val allContacts: LiveData<List<Contact>> = _allContacts

    private fun insertContact(contact: Contact) = viewModelScope.launch {
        contactRepository.addContact(contact)
    }

    private fun removeContact(contact: Contact) = viewModelScope.launch {
        contactRepository.removeContact(contact)

        inputName.value = ""
        inputEmail.value = ""
        isUpdateOrDelete = false
        saveOrUpdateButtonTxt.value = "Save"
        clearAll.value = "Clear All"
    }

    private fun updateContact(contact: Contact) = viewModelScope.launch {
        contactRepository.updateContact(contact)

        inputName.value = ""
        inputEmail.value = ""
        isUpdateOrDelete = false
        saveOrUpdateButtonTxt.value = "Save"
        clearAll.value = "Clear All"
    }

    private fun removeAll() = viewModelScope.launch {
        contactRepository.removeAllContacts()
    }

    fun initUpdateOrDelete(contact: Contact) {
        inputName.value = contact.name
        inputEmail.value = contact.email
        contactToUpdateOrDelete = contact
        isUpdateOrDelete = true
        saveOrUpdateButtonTxt.value = "Update"
        clearAll.value = "Delete"
    }

    fun saveOrUpdate(name: String, email: String) {
        if (isUpdateOrDelete) {
            contactToUpdateOrDelete?.let { contact ->
                contact.name = name
                contact.email = email
                updateContact(contact)
            }
        } else {
            val contact = Contact(0, name, email)
            insertContact(contact)
        }
    }

    fun clearAll() {
        if (isUpdateOrDelete) {
            contactToUpdateOrDelete?.let { removeContact(it) }
        } else {
            removeAll()
        }
    }
}
