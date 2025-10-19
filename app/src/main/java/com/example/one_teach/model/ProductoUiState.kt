package com.example.one_teach.model
import androidx.annotation.DrawableRes

data class ProductoUiState (
    val id: String,
    val category: String,
    val name: String,
    val price: Int,
    val description: String,
    @DrawableRes val image: Int,
    val reviews: List<Review> = emptyList()
)