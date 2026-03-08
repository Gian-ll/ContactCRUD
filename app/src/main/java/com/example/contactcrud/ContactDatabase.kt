package com.example.contactcrud.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.contactcrud.model.Contact

@Database(
    entities = [Contact::class], // ¿Qué tablas tendrá la BD?
    version = 1, // ¿Qué versión es esta BD?
    exportSchema = false // ¿Guardar esquema en archivo?
)

abstract class ContactDatabase : RoomDatabase(){
    abstract fun contactDao(): ContactDao

    companion object{
        @Volatile
        private var INSTANCE: ContactDatabase? = null

        fun getDatabase(context: Context): ContactDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactDatabase::class.java,
                    "contact_database" // NOMBRE del archivo de BD
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}