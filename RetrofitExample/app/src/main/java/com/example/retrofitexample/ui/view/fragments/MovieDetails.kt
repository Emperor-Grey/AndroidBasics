package com.example.retrofitexample.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.retrofitexample.R
import com.example.retrofitexample.data.api.RetrofitInstance
import com.example.retrofitexample.ui.viewmodel.MovieDetailsViewModel
import com.example.retrofitexample.ui.viewmodel.MovieDetailsViewModelFactory
import kotlinx.coroutines.launch

class MovieDetails : Fragment() {

    private val args: MovieDetailsArgs by navArgs()
    private lateinit var movieDetailsViewModel: MovieDetailsViewModel

    private lateinit var progressBar: ProgressBar
    private lateinit var posterImage: ImageView
    private lateinit var title: TextView
    private lateinit var description: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_details, container, false)

        posterImage = view.findViewById(R.id.posterImage)
        title = view.findViewById(R.id.heading)
        description = view.findViewById(R.id.movieDescription)
        progressBar = view.findViewById(R.id.progressBar)

        val movieId = args.movieId
        val retrofitInstance = RetrofitInstance()
        movieDetailsViewModel = ViewModelProvider(
            requireActivity(), MovieDetailsViewModelFactory(retrofitInstance = retrofitInstance)
        ).get(MovieDetailsViewModel::class.java)

        movieDetailsViewModel.fetchMovieDetails(movieId)

        observeViewModel()

        return view
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            movieDetailsViewModel.movieDetails.collect { movieDetails ->
                if (movieDetails != null) {
                    progressBar.visibility = View.GONE

                    Glide.with(requireContext())
                        .load("https://image.tmdb.org/t/p/w500${movieDetails.backdrop_path}")
                        .error(R.drawable.error_img).into(posterImage)
                    title.text = movieDetails.title
                    description.text = movieDetails.description
                } else {
                    progressBar.visibility = View.VISIBLE
                }
            }
        }

        lifecycleScope.launch {
            movieDetailsViewModel.showLoading.collect { isLoading ->
                progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }

        lifecycleScope.launch {
            movieDetailsViewModel.showErrorToast.collect { hasError ->
                Toast.makeText(requireContext(), "Error: $hasError", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
