package com.example.roomexamplewithflows.ui.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roomexamplewithflows.R
import com.example.roomexamplewithflows.data.database.ContactDataBase
import com.example.roomexamplewithflows.repository.ContactRepository
import com.example.roomexamplewithflows.ui.adapter.ContactAdapter
import com.example.roomexamplewithflows.ui.viewmodel.ContactViewModel
import com.example.roomexamplewithflows.ui.viewmodel.ContactViewModelFactory
import kotlinx.coroutines.launch

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
        val contactRecyclerView = findViewById<RecyclerView>(R.id.mainRecyclerView)

        val contactDao = ContactDataBase.getInstance(applicationContext).contactDao
        val contactRepository = ContactRepository(contactDao)
        val factory = ContactViewModelFactory(contactRepository)
        contactViewModel =
            ViewModelProvider(this@MainActivity, factory).get(ContactViewModel::class.java)

        setUpRecyclerView(contactRecyclerView)

        lifecycleScope.launch {
            contactViewModel.inputName.collect { nameText ->
                name.setText(nameText)
            }
        }

        lifecycleScope.launch {
            contactViewModel.inputEmail.collect { emailText ->
                email.setText(emailText)
            }
        }

        lifecycleScope.launch {
            contactViewModel.saveOrUpdateButtonTxt.collect { buttonText ->
                save.text = buttonText
            }
        }

        lifecycleScope.launch {
            contactViewModel.clearAll.collect { buttonText ->
                clearAll.text = buttonText
            }
        }

        save.setOnClickListener {
            contactViewModel.saveOrUpdate(name.text.toString(), email.text.toString())
            contactRecyclerView.adapter?.notifyDataSetChanged()
        }

        clearAll.setOnClickListener {
            contactViewModel.clearAllOrDelete()
        }
    }


    private fun setUpRecyclerView(contactRecyclerView: RecyclerView) {
        contactRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        lifecycleScope.launch {
            contactViewModel.contacts.collect { contacts ->
                contacts.let {
                    contactRecyclerView.adapter =
                        ContactAdapter(contacts, onClickListener = { contact ->
                            contactViewModel.initUpdateOrDelete(contact)
                        })
                }
            }
        }

    }
}
