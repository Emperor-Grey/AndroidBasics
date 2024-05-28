package com.example.roomexamplewithflows.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomexamplewithflows.data.model.Contact
import com.example.roomexamplewithflows.repository.ContactRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ContactViewModel(private val contactRepository: ContactRepository) : ViewModel() {

    private var isUpdateOrDelete = false
    private var contactToUpdateOrDelete: Contact? = null

    private val _inputName = MutableStateFlow("")
    val inputName: StateFlow<String> = _inputName

    private val _inputEmail = MutableStateFlow("")
    val inputEmail: StateFlow<String> = _inputEmail

    private val _saveOrUpdateButtonTxt = MutableStateFlow("Save")
    val saveOrUpdateButtonTxt: StateFlow<String> = _saveOrUpdateButtonTxt

    private val _clearAll = MutableStateFlow("Clear All")
    val clearAll: StateFlow<String> = _clearAll


    private val _contacts = MutableStateFlow<List<Contact>>(emptyList())
    val contacts: StateFlow<List<Contact>> = _contacts

    init {
        viewModelScope.launch {
            contactRepository.contacts.collect {
                _contacts.value = it
            }
        }
    }


    private fun addContact(contact: Contact) = viewModelScope.launch {
        contactRepository.insertContact(contact)
    }

    private fun removeContact(contact: Contact) = viewModelScope.launch {
        contactRepository.removeContact(contact)

        _inputName.value = ""
        _inputEmail.value = ""
        isUpdateOrDelete = false
        _saveOrUpdateButtonTxt.value = "Save"
        _clearAll.value = "Clear All"
    }

    private fun updateContact(contact: Contact) = viewModelScope.launch {
        contactRepository.updateContact(contact)

        _inputName.value = ""
        _inputEmail.value = ""
        isUpdateOrDelete = false
        _saveOrUpdateButtonTxt.value = "Save"
        _clearAll.value = "Clear All"
    }

    private fun clearAll() = viewModelScope.launch {
        contactRepository.deleteAll()
    }

    fun initUpdateOrDelete(contact: Contact) {
        _inputName.value = contact.name
        _inputEmail.value = contact.email
        contactToUpdateOrDelete = contact
        isUpdateOrDelete = true
        _saveOrUpdateButtonTxt.value = "Update"
        _clearAll.value = "Delete"
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
            addContact(contact)
        }
    }

    fun clearAllOrDelete() {
        if (isUpdateOrDelete) {
            contactToUpdateOrDelete?.let {
                removeContact(it)
            }
        } else {
            clearAll()
        }
    }
}
