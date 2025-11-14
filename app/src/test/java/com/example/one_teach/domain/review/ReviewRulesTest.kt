package com.example.one_teach.domain.review

import com.example.one_teach.ui.components.review.averageRating
import com.example.one_teach.ui.components.review.canSubmitReview
import com.example.one_teach.ui.components.review.isValidComment
import com.example.one_teach.ui.components.review.isValidRating
import org.junit.Assert.*
import org.junit.Test

class ReviewRulesTest {

    @Test
    fun `rating valido esta entre 1 y 5`() {
        assertTrue(isValidRating(1))
        assertTrue(isValidRating(3))
        assertTrue(isValidRating(5))
    }

    @Test
    fun `rating invalido fuera de rango`() {
        assertFalse(isValidRating(0))
        assertFalse(isValidRating(-1))
        assertFalse(isValidRating(6))
        assertFalse(isValidRating(10))
    }

    @Test
    fun `comentario invalido si es vacio o muy corto`() {
        assertFalse(isValidComment(""))
        assertFalse(isValidComment("   "))
        assertFalse(isValidComment("hey"))      // 3 caracteres
        assertTrue(isValidComment("bueno"))     // 5 caracteres
        assertTrue(isValidComment("muy buen juego"))
    }

    @Test
    fun `solo se puede enviar reseña con datos validos`() {
        // válido
        assertTrue(canSubmitReview("Gran juego, muy entretenido", 5))

        // rating inválido
        assertFalse(canSubmitReview("Buen juego", 0))
        assertFalse(canSubmitReview("Buen juego", 6))

        // comentario inválido
        assertFalse(canSubmitReview("ok", 4))
        assertFalse(canSubmitReview("   ", 4))
    }

    @Test
    fun `promedio es 0 cuando no hay ratings`() {
        val avg = averageRating(emptyList())
        assertEquals(0.0, avg, 0.0001)
    }

    @Test
    fun `promedio se calcula correctamente con ratings validos`() {
        val avg = averageRating(listOf(5, 4, 3))
        assertEquals(4.0, avg, 0.0001)
    }

    @Test
    fun `promedio ignora ratings invalidos`() {
        val avg = averageRating(listOf(5, 7, 0, 4)) // 7 y 0 son inválidos
        // solo considera 5 y 4 -> promedio 4.5
        assertEquals(4.5, avg, 0.0001)
    }
}
