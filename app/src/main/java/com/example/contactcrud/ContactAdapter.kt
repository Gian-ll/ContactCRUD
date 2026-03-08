package com.example.contactcrud.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.contactcrud.R
import com.example.contactcrud.model.Contact

class ContactAdapter(private val onContactClick: (Contact) -> Unit) :
    ListAdapter<Contact, ContactAdapter.ContactViewHolder>(ContactDiffCallback()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder{
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int){
        val contact = getItem(position)
        holder.bind(contact)
        holder.itemView.setOnClickListener{
            onContactClick(contact)
        }
    }

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val nameTextView: TextView = itemView.findViewById(R.id.textViewName)
        private val phoneTextView: TextView = itemView.findViewById(R.id.textViewPhone)
        private val emailTextView: TextView = itemView.findViewById(R.id.textViewEmail)

        fun bind(contact: Contact) {
            nameTextView.text = contact.name
            phoneTextView.text = contact.phone
            emailTextView.text = contact.email
        }
    }
}

class ContactDiffCallback : DiffUtil.ItemCallback<Contact>() {
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem == newItem
    }
}