package com.example.retrofitexample.data.api

import com.example.retrofitexample.data.model.Movies
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    //https://api.themoviedb.org/3/movie/popular?language=en-US&page=1&api_key={api_key}

    @GET("/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int, @Query("api_key") apiKey: String
    ): Movies

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/movie"
    }
}
