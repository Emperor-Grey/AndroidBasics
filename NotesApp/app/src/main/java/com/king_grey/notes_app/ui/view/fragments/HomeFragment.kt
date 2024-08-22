@file:Suppress("DEPRECATION")

package com.king_grey.notes_app.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.king_grey.notes_app.R
import com.king_grey.notes_app.databinding.FragmentHomeBinding
import com.king_grey.notes_app.ui.adapter.NoteAdapter
import com.king_grey.notes_app.ui.view.MainActivity
import com.king_grey.notes_app.ui.viewmodels.NoteViewModel
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter
//    private val menuHost: MenuHost = requireActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteViewModel = (activity as MainActivity).noteViewModel  // check main activity

        setupRecyclerView()
        binding.fab.setOnClickListener {
            it.findNavController().navigate(
                R.id.navigateToNoteFragment
            )
        }
//        menuHost.addMenuProvider(object : MenuProvider {
//            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
//                menuInflater.inflate(R.menu.home_menu, menu)
//            }
//
//            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
//                when (menuItem.itemId) {
//                    (R.id.menu_search) -> {
//                        noteViewModel.searchNote()
//                        return true
//                    }
//
//                    R.id.menu_removeAll -> {
//                        noteViewModel.clearAll()
//                        return true
//                    }
//                }
//                return false
//            }
//
//        })

    }


    private fun setupRecyclerView() {
        noteAdapter = NoteAdapter(noteViewModel)
        binding.noteList.apply {
            layoutManager = StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL,
            )
            setHasFixedSize(true)
            adapter = noteAdapter
        }

        activity?.let {
            lifecycleScope.launch {
                noteViewModel.notes.collect { notes ->
                    noteAdapter.differ.submitList(notes)
                    if (notes.isNotEmpty()) {
                        binding.emptyCardView.visibility = View.GONE
                        binding.noteList.visibility = View.VISIBLE
                    } else {
                        binding.emptyCardView.visibility = View.VISIBLE
                        binding.noteList.visibility = View.GONE
                    }
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        menu.clear()
        inflater.inflate(R.menu.home_menu, menu)

        val searchItem = menu.findItem(R.id.menu_search).actionView as SearchView
//        searchItem.isSubmitButtonEnabled = false
        searchItem.setOnQueryTextListener(this)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_removeAll -> {
                MaterialAlertDialogBuilder(requireContext()).apply {
                    setTitle("Delete Note")
                    setMessage("Are you sure you want to delete all notes?")
                    setPositiveButton("Yes") { _, _ ->
                        noteViewModel.clearAll()
                    }
                    setNegativeButton("No", null)
                }.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            noteViewModel.searchNote(it)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let {
            noteViewModel.searchNote(it)
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
