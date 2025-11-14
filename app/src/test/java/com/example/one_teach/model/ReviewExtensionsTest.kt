package com.example.one_teach.model

import org.junit.Assert.assertEquals
import org.junit.Test

class ReviewExtensionsTest {

    @Test
    fun `averageRating en lista vacia devuelve 0`() {
        val reviews = emptyList<Review>()

        val avg = reviews.averageRating()

        assertEquals(0f, avg)
    }

    @Test
    fun `averageRating con una sola resena devuelve su mismo rating`() {
        val reviews = listOf(
            Review(
                author = "Miguel",
                rating = 5,
                comment = "Excelente juego"
            )
        )

        val avg = reviews.averageRating()

        assertEquals(5f, avg)
    }

    @Test
    fun `averageRating calcula correctamente el promedio con varias resenas`() {
        val reviews = listOf(
            Review(author = "User1", rating = 5, comment = "Muy bueno"),
            Review(author = "User2", rating = 3, comment = "Más o menos"),
            Review(author = "User3", rating = 4, comment = "Entrete")
        )

        val avg = reviews.averageRating()

        // (5 + 3 + 4) / 3 = 4.0
        assertEquals(4f, avg)
    }

    @Test
    fun `averageRating admite decimales en el promedio`() {
        val reviews = listOf(
            Review(author = "A", rating = 5, comment = ""),
            Review(author = "B", rating = 4, comment = "")
        )

        val avg = reviews.averageRating()

        // (5 + 4) / 2 = 4.5
        assertEquals(4.5f, avg, 0.0001f)
    }
}
