package com.example.retrofitexample.data.model

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    val adult: Boolean,
    val backdrop_path: String,
    val id: Int,
    val original_language: String,
    @SerializedName("overview") val description: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)
