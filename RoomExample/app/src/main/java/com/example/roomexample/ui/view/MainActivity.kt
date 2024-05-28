package com.example.roomexample.ui.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roomexample.R
import com.example.roomexample.data.database.ContactDatabase
import com.example.roomexample.repository.ContactRepository
import com.example.roomexample.ui.adapters.ContactAdapter
import com.example.roomexample.ui.viewmodel.ContactViewModel
import com.example.roomexample.ui.viewmodel.ContactViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var contactViewModel: ContactViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val name: EditText = findViewById(R.id.name)
        val email: EditText = findViewById(R.id.email)
        val save: Button = findViewById(R.id.save)
        val clearAll: Button = findViewById(R.id.clearAll)

        val contactDao = ContactDatabase.getInstance(applicationContext).contactDao
        val contactRepository = ContactRepository(contactDao)
//        contactViewModel = ViewModelProvider(this@MainActivity).get(ContactViewModel::class.java)
        val factory = ContactViewModelFactory(contactRepository)
        contactViewModel = ViewModelProvider(this@MainActivity, factory).get(
            ContactViewModel::class.java
        )

        setUpRecyclerView()

        name.setText(contactViewModel.inputName.value)
        email.setText(contactViewModel.inputEmail.value)
        save.text = contactViewModel.saveOrUpdateButtonTxt.value
        clearAll.text = contactViewModel.clearAll.value

        contactViewModel.saveOrUpdateButtonTxt.observe(this@MainActivity) { buttonText ->
            save.text = buttonText
        }

        contactViewModel.clearAll.observe(this@MainActivity) { buttonText ->
            clearAll.text = buttonText
        }

        save.setOnClickListener {
            contactViewModel.saveOrUpdate(name.text.toString(), email.text.toString())
        }

        clearAll.setOnClickListener {
            contactViewModel.clearAll()
        }
    }

    private fun setUpRecyclerView() {
        val contactRecyclerView = findViewById<RecyclerView>(R.id.mainRecyclerView)
        contactRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        contactViewModel.allContacts.observe(this@MainActivity) { contacts ->
            contacts?.let {
                contactRecyclerView.adapter =
                    ContactAdapter(contacts, onItemClickListener = { contact ->
                        contactViewModel.initUpdateOrDelete(contact = contact)
                    })
            }
        }
    }
}
