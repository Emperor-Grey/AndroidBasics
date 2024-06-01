package com.example.retrofitexample.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
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
    private lateinit var progressBar: ProgressBar
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        progressBar = view.findViewById(R.id.progressBar)
        progressBar.visibility = View.GONE

        val retrofitInstance = RetrofitInstance()
        movieViewModel = ViewModelProvider(
            requireActivity(), MovieViewModelFactory(
                retrofitInstance
            )
        ).get(MovieViewModel::class.java)

        setUpRecyclerView(view)
        observeViewModel(view)

        return view
    }

    private fun observeViewModel(view: View) {
        lifecycleScope.launch {
            movieViewModel.showLoading.collect { isLoading ->
                progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }

        lifecycleScope.launch {
            movieViewModel.showErrorToast.collect { hasError ->
                if (hasError) {
                    Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        lifecycleScope.launch {
            movieViewModel.movies.collect { movies ->
                movieAdapter.updateMovies(movies)
            }
        }
    }

    private fun setUpRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.movieList)
        recyclerView.layoutManager =
            GridLayoutManager(view.context, 2, GridLayoutManager.VERTICAL, false)
        movieAdapter = MovieAdapter(view.context) {
            val action = HomeDirections.navigateToDetails(movieId = it.id)
            Navigation.findNavController(view).navigate(action)
        }
        recyclerView.adapter = movieAdapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!recyclerView.canScrollVertically(1)) {
                    movieViewModel.fetchNextPage()
                }
            }
        })
    }
}
