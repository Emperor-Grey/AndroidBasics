package com.example.retrofitexample.data.repository

import com.example.retrofitexample.utils.Result
import com.example.retrofitexample.data.model.MovieResponse
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getPopularMovies(page: Int = 1): Flow<Result<List<MovieResponse>>>
    suspend fun getMovieDetails(movieId: Int): Flow<Result<MovieResponse>>
}
