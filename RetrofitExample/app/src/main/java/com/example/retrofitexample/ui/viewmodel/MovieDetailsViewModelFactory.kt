package com.example.retrofitexample.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitexample.data.api.RetrofitInstance
import com.example.retrofitexample.data.repository.MovieRepositoryImpl

class MovieDetailsViewModelFactory(
    private val movieId: Int, private val retrofitInstance: RetrofitInstance
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
            return MovieDetailsViewModel(
                movieId = movieId,
                movieRepository = MovieRepositoryImpl(retrofitInstance = retrofitInstance)
            ) as T
        } else {
            throw IllegalArgumentException("Unknown View Model Class")
        }
    }
}
