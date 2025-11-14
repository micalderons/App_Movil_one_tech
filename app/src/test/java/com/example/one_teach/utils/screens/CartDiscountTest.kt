package com.example.one_teach.utils.screens

import com.example.one_teach.domain.applyDiscountCode
import org.junit.Assert.*
import org.junit.Test

class CartDiscountTest {

    @Test
    fun `codigo duocuc cl aplica 10 porciento`() {
        // Arrange
        val subtotal = 59_980

        // Act
        val result = applyDiscountCode(subtotal, "duocuc.cl")

        // Assert
        assertEquals("Debe marcar que el descuento fue aplicado",  true, result.discountAmount > 0)
        val expectedDiscount = (subtotal * 0.10).toInt()
        assertEquals("Descuento debe ser 10% del subtotal", expectedDiscount, result.discountAmount)
        assertEquals("Total final debe ser subtotal - descuento",
            subtotal - expectedDiscount,
            result.finalTotal
        )
    }

    @Test
    fun `codigo con mayusculas o espacios tambien funciona`() {
        val subtotal = 30_000

        val result = applyDiscountCode(subtotal, "  DUOCUC.CL  ")

        assertTrue("Debe seguir aplicando descuento", result.discountAmount > 0)
        val expectedDiscount = (subtotal * 0.10).toInt()
        assertEquals(expectedDiscount, result.discountAmount)
        assertEquals(subtotal - expectedDiscount, result.finalTotal)
    }

    @Test
    fun `codigo invalido no aplica descuento`() {
        val subtotal = 50_000

        val result = applyDiscountCode(subtotal, "otroCodigo")

        assertEquals("No debe haber descuento", 0, result.discountAmount)
        assertEquals("Total final debe ser igual al subtotal", subtotal, result.finalTotal)
    }
}
