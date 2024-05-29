package com.example.retrofitexample.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitexample.data.api.RetrofitInstance
import com.example.retrofitexample.data.repository.MovieRepositoryImpl

class MovieViewModelFactory(
    private val retrofitInstance: RetrofitInstance
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            return MovieViewModel(MovieRepositoryImpl(retrofitInstance)) as T
        } else {
            throw IllegalArgumentException("Unknown View Model Class")
        }
    }
}
