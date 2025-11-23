package com.example.one_teach.model

fun List<Review>.averageRating(): Float {
    if (isEmpty()) return 0f
    val total = this.sumOf { it.rating }
    return total.toFloat() / size.toFloat()
}
