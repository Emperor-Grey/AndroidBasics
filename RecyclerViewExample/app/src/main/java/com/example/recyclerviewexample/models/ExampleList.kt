package com.example.recyclerviewexample.models

import androidx.annotation.DrawableRes

data class ExampleList(
    @DrawableRes val image: Int,
    val title: String,
    val description: String,
)
