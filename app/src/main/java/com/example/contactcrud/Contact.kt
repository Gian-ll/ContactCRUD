package com.example.contactcrud.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String, // Antes era "name"
    val phone: String,
    val email: String // Campo nuevo
)