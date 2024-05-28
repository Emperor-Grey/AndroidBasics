package com.example.roomexamplewithflows.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.roomexamplewithflows.repository.ContactRepository

class ContactViewModelFactory(private val contactRepository: ContactRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactViewModel::class.java)) {
            return ContactViewModel(contactRepository = contactRepository) as T
        } else {
            throw IllegalArgumentException("Unknown View Model Class")
        }
    }
}
