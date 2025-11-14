package com.example.one_teach.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CartDiscountTest {

    @Test
    fun `codigo duocuc cl aplica 10 porciento`() {
        val subtotal = 59980
        val result = applyDiscountCode(subtotal, "duocuc.cl")

        assertTrue(result.discountApplied)
        assertEquals((subtotal * 0.10).toInt(), result.discountAmount)
        assertEquals(subtotal - result.discountAmount, result.finalTotal)
    }

    @Test
    fun `codigo en mayusculas o con espacios igual funciona`() {
        val subtotal = 30000
        val result = applyDiscountCode(subtotal, "  DUOCUC.CL  ")

        assertTrue(result.discountApplied)
        assertEquals((subtotal * 0.10).toInt(), result.discountAmount)
    }

    @Test
    fun `codigo incorrecto no aplica descuento`() {
        val subtotal = 50000
        val result = applyDiscountCode(subtotal, "otroCodigo")

        assertFalse(result.discountApplied)
        assertEquals(0, result.discountAmount)
        assertEquals(subtotal, result.finalTotal)
    }
}
