package com.example.notesapp.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.data.model.Note
import com.example.notesapp.databinding.NoteItemBinding
import com.example.notesapp.ui.view.fragments.HomeFragmentDirections
import com.example.notesapp.ui.viewmodels.NoteViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.Random

class NoteAdapter(
    private val noteViewModel: NoteViewModel
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = differ.currentList[position]

        holder.itemBinding.tvnoteTitle.text = note.title
        holder.itemBinding.tvNoteDesc.text = note.description

        val random = Random()
        val color = Color.argb(
            255, random.nextInt(256), random.nextInt(256), random.nextInt(256)
        )

        holder.itemBinding.ibColor.setBackgroundColor(color)
        holder.itemView.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToUpdateNoteFragment(note)
            it.findNavController().navigate(action)
        }
        holder.itemView.setOnLongClickListener {
            MaterialAlertDialogBuilder(it.context).apply {
                setTitle("Delete Note")
                setMessage("Are you sure you want to delete this note?")
                setPositiveButton("Yes, Delete") { _, _ ->
                    noteViewModel.removeNote(note = note)
                }
                setNegativeButton("No", null)
            }.show()
            true
        }
    }


    inner class NoteViewHolder(val itemBinding: NoteItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem && oldItem.description == newItem.description && oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    })
}
