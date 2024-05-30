package com.example.retrofitexample.data.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/movie/"
    }

    private val httpClient: OkHttpClient by lazy {
        val httpLoggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()
    }

    private fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(httpClient).build()
    }

    val instance = getRetrofitInstance()

    fun getMovieService(): MovieService {
        return getRetrofitInstance().create(MovieService::class.java)
    }
}
