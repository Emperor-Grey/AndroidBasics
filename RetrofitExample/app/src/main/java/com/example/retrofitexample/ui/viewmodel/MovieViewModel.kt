package com.example.retrofitexample.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitexample.data.api.Result
import com.example.retrofitexample.data.model.MovieResponse
import com.example.retrofitexample.data.repository.MovieRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val _movies = MutableStateFlow<List<MovieResponse>>(emptyList())
    val movies = _movies.asStateFlow()

    private val _showErrorToast = Channel<Boolean>()
    val showErrorToast = _showErrorToast.receiveAsFlow()

    init {
        viewModelScope.launch {
            movieRepository.getPopularMovies().collect { result ->
                when (result) {
                    is Result.Error -> {
                        _showErrorToast.send(true)
                    }

                    is Result.Success -> {
                        result.data?.let { results ->
                            _movies.update { results }
                        }
                    }
                }
            }
        }
    }
}
