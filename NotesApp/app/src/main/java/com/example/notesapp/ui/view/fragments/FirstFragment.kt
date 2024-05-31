package com.example.notesapp.ui.view.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.notesapp.R

class FirstFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_first, container, false)

        // Hide the action bar
        (activity as? AppCompatActivity)?.supportActionBar?.hide()

        // Navigate to the next screen after 4 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.action_firstFragment_to_homeFragment)
        }, 3000)

        return v
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Show the action bar again when leaving the fragment
        (activity as? AppCompatActivity)?.supportActionBar?.show()
    }
}
