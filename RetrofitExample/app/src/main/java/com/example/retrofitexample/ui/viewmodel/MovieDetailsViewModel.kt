package com.example.retrofitexample.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitexample.data.model.MovieResponse
import com.example.retrofitexample.data.repository.MovieRepository
import com.example.retrofitexample.utils.Result
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val movieId: Int, private val movieRepository: MovieRepository
) : ViewModel() {
    private val _movieDetails = MutableStateFlow<MovieResponse?>(null)
    val movieDetails = _movieDetails.asStateFlow()

    private val _showErrorToast = Channel<Boolean>()
    val showErrorToast = _showErrorToast.receiveAsFlow()

    private val _showLoading = Channel<Boolean>()
    val showLoading = _showLoading.receiveAsFlow()

    init {
        viewModelScope.launch {
            movieRepository.getMovieDetails(movieId = movieId).collect { result ->
                when (result) {
                    is Result.Error -> {
                        _showErrorToast.send(true)
                        _showLoading.send(true)
                    }

                    is Result.Success -> {
                        result.data?.let { results ->
                            _movieDetails.value = results
                        }
                        _showLoading.send(false)
                    }
                }
            }
        }
    }
}
