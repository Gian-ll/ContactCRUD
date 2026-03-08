package com.example.contactcrud.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.contactcrud.model.Contact

@Dao
interface ContactDao{
    // CREATE
    @Insert
    suspend fun insert(contact: Contact)

    // READ
    @Query("SELECT * FROM contact ORDER BY Name ASC")
    fun getAllContacts(): LiveData<List<Contact>>

    @Query("SELECT * FROM contact WHERE id = :id")
    suspend fun getContactById(id: Int): Contact?

    // UPDATE
    @Update
    suspend fun update(contact: Contact)

    // DELETE
    @Delete
    suspend fun delete(contact: Contact)

    @Query("DELETE FROM contact")
    suspend fun deleteAllContacts()
}