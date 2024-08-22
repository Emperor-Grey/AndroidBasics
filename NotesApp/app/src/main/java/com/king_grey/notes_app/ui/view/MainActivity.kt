package com.king_grey.notes_app.ui.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.king_grey.notes_app.R
import com.king_grey.notes_app.data.database.NoteDataBase
import com.king_grey.notes_app.databinding.ActivityMainBinding
import com.king_grey.notes_app.repository.NoteRepository
import com.king_grey.notes_app.ui.viewmodels.NoteViewModel
import com.king_grey.notes_app.ui.viewmodels.NoteViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var noteViewModel: NoteViewModel  // needed for home fragment when casting
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setUpViewModel()
    }

    private fun setUpViewModel() {
        val noteRepository = NoteRepository(NoteDataBase.getInstance(this))
        noteViewModel = ViewModelProvider(
            this, NoteViewModelFactory(app = application, noteRepository = noteRepository)
        )[NoteViewModel::class.java]
    }
}
