package com.example.one_teach.model

/**
 * Promedio de rating de una lista de reseñas.
 * - Si la lista está vacía, devuelve 0f.
 * - rating se supone en el rango 1..5.
 */
fun List<Review>.averageRating(): Float {
    if (isEmpty()) return 0f
    val total = this.sumOf { it.rating }
    return total.toFloat() / size.toFloat()
}
