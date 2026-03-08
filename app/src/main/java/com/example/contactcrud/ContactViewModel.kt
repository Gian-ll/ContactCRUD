package com.example.contactcrud.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.contactcrud.database.ContactDatabase
import com.example.contactcrud.model.Contact
import com.example.contactcrud.repository.ContactRepository
import kotlinx.coroutines.launch

class ContactViewModel(application: Application) : AndroidViewModel(application){
    private val repository: ContactRepository
    val allContacts: LiveData<List<Contact>>

    init{
        val contactDao = ContactDatabase.getDatabase(application).contactDao()
        repository = ContactRepository(contactDao)
        allContacts = repository.allContacts
    }

    fun insert(contact: Contact) = viewModelScope.launch{
        repository.insert(contact)
    }

    fun update(contact: Contact) = viewModelScope.launch{
        repository.update(contact)
    }

    fun delete(contact: Contact) = viewModelScope.launch{
        repository.delete(contact)
    }

    suspend fun getContactById(id: Int): Contact?{
        return repository.getContactById(id)
    }

}