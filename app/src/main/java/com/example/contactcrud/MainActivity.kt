package com.example.contactcrud

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactcrud.adapter.ContactAdapter
import com.example.contactcrud.model.Contact
import com.example.contactcrud.viewmodel.ContactViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var contactViewModel: ContactViewModel
    private lateinit var contactAdapter: ContactAdapter

    private lateinit var editTextName: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var buttonAdd: Button
    private lateinit var buttonUpdate: Button

    private var contactToUpdate: Contact? = null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupRecyclerView()
        setupViewModel()
        setupClickListeners()
    }

    private fun initViews(){
        editTextName = findViewById(R.id.editTextName)
        editTextPhone = findViewById(R.id.editTextPhone)
        editTextEmail = findViewById(R.id.editTextEmail)
        buttonAdd = findViewById(R.id.buttonAdd)
        buttonUpdate = findViewById(R.id.buttonUpdate)
    }

    private fun setupRecyclerView(){
        contactAdapter = ContactAdapter{contact ->
            showContactOptions(contact)
        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewContacts)
        recyclerView.adapter = contactAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setupViewModel(){
        contactViewModel = ViewModelProvider(this)[ContactViewModel::class.java]
        contactViewModel.allContacts.observe(this){contacts ->
            contactAdapter.submitList(contacts)
        }
    }

    private fun setupClickListeners(){
        buttonAdd.setOnClickListener{
            addContact()
        }

        buttonUpdate.setOnClickListener{
            updateContact()
        }
    }

    private fun addContact(){
        val name = editTextName.text.toString().trim()
        val phone = editTextPhone.text.toString().trim()
        val email = editTextEmail.text.toString().trim()

        if (name.isNotEmpty() && phone.isNotEmpty() && email.isNotEmpty()){
            val contact = Contact(name = name, phone = phone, email = email)
            contactViewModel.insert(contact)
            clearInputFields()
            Toast.makeText(this, "Contacto agregado", Toast.LENGTH_SHORT).show()
        } else{
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateContact(){
        val name = editTextName.text.toString().trim()
        val phone = editTextPhone.text.toString().trim()
        val email = editTextEmail.text.toString().trim()

        if (name.isNotEmpty() && phone.isNotEmpty() && email.isNotEmpty() &&
            contactToUpdate != null){
            val updatedContact = contactToUpdate!!.copy(name = name, phone = phone, email = email)
            contactViewModel.update(updatedContact)
            clearInputFields()
            cancelUpdate()
            Toast.makeText(this, "Contacto actualizado", Toast.LENGTH_SHORT).show()
        } else{
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showContactOptions(contact: Contact){
        val options = arrayOf("Editar", "Eliminar")

        AlertDialog.Builder(this)
            .setTitle("Opciones para ${contact.name}")
            .setItems(options){ _, which ->
                when (which){
                    0 -> editContact(contact)
                    1 -> deleteContact(contact)
                }
            }
            .show()
    }

    private fun editContact(contact: Contact){
        contactToUpdate = contact
        editTextName.setText(contact.name)
        editTextPhone.setText(contact.phone)
        editTextEmail.setText(contact.email)

        buttonAdd.visibility = android.view.View.GONE
        buttonUpdate.visibility = android.view.View.VISIBLE
    }

    private fun deleteContact(contact: Contact){
        AlertDialog.Builder(this)
            .setTitle("Eliminar contacto")
            .setMessage("¿Estás seguro de eliminar a ${contact.name}?")
            .setPositiveButton("Eliminar"){_, _ ->
                contactViewModel.delete(contact)
                Toast.makeText(this, "Contacto eliminado", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun cancelUpdate(){
        contactToUpdate = null
        buttonAdd.visibility = android.view.View.VISIBLE
        buttonUpdate.visibility = android.view.View.GONE
    }

    private fun clearInputFields(){
        editTextName.text.clear()
        editTextPhone.text.clear()
        editTextEmail.text.clear()
    }

    override fun onBackPressed(){
        if (contactToUpdate != null){
            clearInputFields()
            cancelUpdate()
        } else{
            super.onBackPressed()
        }
    }
}