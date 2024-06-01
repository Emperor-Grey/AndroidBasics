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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val _movies = MutableStateFlow<List<MovieResponse>>(emptyList())
    val movies = _movies.asStateFlow()

    private val _showErrorToast = Channel<Boolean>()
    val showErrorToast = _showErrorToast.receiveAsFlow()

    private val _showLoading = MutableStateFlow(false)
    val showLoading = _showLoading.asStateFlow()

    private var currentPage: Int = 1
    private var isLastPage: Boolean = false

    init {
        viewModelScope.launch {
            movieRepository.getPopularMovies(page = currentPage).collect { result ->
                when (result) {
                    is Result.Error -> {
                        _showErrorToast.send(true)
                        _showLoading.value = false
                    }

                    is Result.Success -> {
                        result.data?.let { results ->
                            _movies.update { results }
                        }
                        _showLoading.value = false
                    }

                    is Result.Loading -> {
                        _showLoading.value = true
                    }
                }
            }
        }
    }

    fun fetchNextPage() {
        if (!isLastPage) {
            currentPage++
            viewModelScope.launch {
                movieRepository.getPopularMovies(currentPage).collect { result ->
                    when (result) {
                        is Result.Error -> {
                            _showErrorToast.send(true)
                            _showLoading.value = false
                        }

                        is Result.Success -> {
                            _showLoading.value = false
                            result.data?.let { movies ->
                                if (movies.isEmpty()) {
                                    isLastPage = true
                                } else {
                                    _movies.update { newMovie ->
                                        (movies + newMovie).toSet().toList()
                                    }
                                }
                            }
                        }

                        is Result.Loading -> {
                            _showLoading.value = true
                        }
                    }
                }
            }
        }
    }
}
