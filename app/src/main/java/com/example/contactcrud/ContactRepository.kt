package com.example.contactcrud.repository

import androidx.lifecycle.LiveData
import com.example.contactcrud.database.ContactDao
import com.example.contactcrud.model.Contact

class ContactRepository(private val contactDao: ContactDao) {
    val allContacts: LiveData<List<Contact>> = contactDao.getAllContacts()

    suspend fun insert(contact: Contact){
        contactDao.insert(contact)
    }

    suspend fun update(contact: Contact){
        contactDao.update(contact)
    }

    suspend fun delete(contact: Contact){
        contactDao.delete(contact)
    }

    suspend fun getContactById(id: Int): Contact?{
        return contactDao.getContactById(id)
    }
}