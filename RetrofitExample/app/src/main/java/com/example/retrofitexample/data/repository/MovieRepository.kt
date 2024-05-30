package com.example.retrofitexample.data.repository

import com.example.retrofitexample.data.api.Result
import com.example.retrofitexample.data.model.MovieResponse
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getPopularMovies(page: Int = 1): Flow<Result<List<MovieResponse>>>
}
