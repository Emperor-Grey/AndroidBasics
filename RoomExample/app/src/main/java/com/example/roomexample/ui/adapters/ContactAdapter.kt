package com.example.roomexample.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.roomexample.R
import com.example.roomexample.data.model.Contact

class ContactAdapter(
    private val contactList: List<Contact>,
    private val onItemClickListener: (Contact) -> Unit,
) : RecyclerView.Adapter<ContactAdapter.ContractViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContractViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ContractViewHolder(v)
    }


    override fun onBindViewHolder(holder: ContractViewHolder, position: Int) {
        val contact = contactList[position]
        holder.name.text = contact.name
        holder.email.text = contact.email

        holder.itemView.setOnClickListener {
            onItemClickListener(contact)
        }
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    inner class ContractViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val email: TextView = itemView.findViewById(R.id.email)
    }

}
