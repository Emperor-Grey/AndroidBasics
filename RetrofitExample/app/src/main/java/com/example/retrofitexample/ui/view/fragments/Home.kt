package com.example.retrofitexample.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitexample.R
import com.example.retrofitexample.data.api.RetrofitInstance
import com.example.retrofitexample.ui.view.adapters.MovieAdapter
import com.example.retrofitexample.ui.viewmodel.MovieViewModel
import com.example.retrofitexample.ui.viewmodel.MovieViewModelFactory
import kotlinx.coroutines.launch

class Home : Fragment() {

    private lateinit var movieViewModel: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val retrofitInstance = RetrofitInstance()
        movieViewModel = ViewModelProvider(
            requireActivity(), MovieViewModelFactory(
                retrofitInstance
            )
        ).get(MovieViewModel::class.java)
        setUpRecyclerView(view)
        return view
    }

    private fun setUpRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.movieList)
        recyclerView.layoutManager =
            GridLayoutManager(view.context, 2, GridLayoutManager.VERTICAL, false)
        lifecycleScope.launch {
            movieViewModel.movies.collect { movies ->
                movies.let {
                    recyclerView.adapter = MovieAdapter(view.context, movieViewModel.movies.value) {
                        // TODO()
                    }
                }
            }
        }
    }
}
