package com.king_grey.notes_app.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.king_grey.notes_app.R
import com.king_grey.notes_app.data.model.Note
import com.king_grey.notes_app.databinding.FragmentUpdateNoteBinding
import com.king_grey.notes_app.ui.view.MainActivity
import com.king_grey.notes_app.ui.viewmodels.NoteViewModel

class UpdateNoteFragment : Fragment(R.layout.fragment_update_note) {
    private var _binding: FragmentUpdateNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var currentNote: Note
    private lateinit var noteViewModel: NoteViewModel

    private val args: UpdateNoteFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteViewModel = (activity as MainActivity).noteViewModel
        currentNote = args.notes!!

        binding.UpdateNoteTitle.setText(currentNote.title)
        binding.UpdateNoteDesc.setText(currentNote.description)

        // if user updates the note
        binding.fabDone.setOnClickListener {
            val title = binding.UpdateNoteTitle.text.toString().trim()
            val description = binding.UpdateNoteDesc.text.toString().trim()

            if (title.isNotEmpty()) {
                val note = Note(currentNote.id, title, description)

                noteViewModel.updateNote(note)

                view.findNavController().navigate(R.id.action_updateNoteFragment_to_homeFragment)
            } else {
                Toast.makeText(requireContext(), "Change Something", Toast.LENGTH_SHORT).show()
            }
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.update_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_delete -> {
                deleteNote()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun deleteNote() {
        MaterialAlertDialogBuilder(requireContext()).apply {
            setTitle("Delete Note")
            setMessage("Are you sure you want to delete this note?")
            setPositiveButton("Yes, Delete") { _, _ ->
                noteViewModel.removeNote(note = currentNote)
                findNavController().navigate(R.id.action_updateNoteFragment_to_homeFragment)
            }
            setNegativeButton("No", null)
        }.show()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
