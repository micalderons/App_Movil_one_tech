package com.example.one_teach.viewmodel

import com.example.one_teach.model.CartItem
import com.example.one_teach.model.ProductoUiState
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ProductViewModelTest {

    private lateinit var vm: ProductoViewModel

    @Before
    fun setup() {
        vm = ProductoViewModel()
    }

    private fun testProduct(
        id: String = "1",
        name: String = "Producto Test",
        price: Int = 5000
    ) = ProductoUiState(
        id = id,
        name = name,
        price = price,
        category = "Juegos",
        description = "Producto de prueba",
        image = 0,
        reviews = emptyList()
    )

    @Test
    fun `agregar producto al carrito`() {
        val p = testProduct()

        vm.addToCart(p)

        val cart = vm.cart.value
        assertEquals(1, cart.size)
        assertEquals("1", cart[0].id)
        assertEquals(1, cart[0].qty)
    }

    @Test
    fun `agregar dos veces suma cantidades`() {
        val p = testProduct()

        vm.addToCart(p)
        vm.addToCart(p)

        val item = vm.cart.value.first()
        assertEquals(2, item.qty)
    }

    @Test
    fun `updateQty cambia la cantidad correctamente`() {
        val p = testProduct()

        vm.addToCart(p)         // qty = 1
        vm.updateQty("1", 5)    // nueva cantidad

        val item = vm.cart.value.first()
        assertEquals(5, item.qty)
    }

    @Test
    fun `updateQty en cero elimina el item`() {
        val p = testProduct()

        vm.addToCart(p)
        vm.updateQty("1", 0)

        val cart = vm.cart.value
        assertTrue(cart.isEmpty())
    }

    @Test
    fun `removeFromCart elimina el producto`() {
        val p = testProduct()

        vm.addToCart(p)
        vm.removeFromCart("1")

        assertTrue(vm.cart.value.isEmpty())
    }

    @Test
    fun `clearCart vacia completamente el carrito`() {
        val p1 = testProduct(id = "1")
        val p2 = testProduct(id = "2")

        vm.addToCart(p1)
        vm.addToCart(p2)

        vm.clearCart()

        assertTrue(vm.cart.value.isEmpty())
    }

    @Test
    fun `agregar producto con cantidad personalizada funciona`() {
        val p = testProduct()

        vm.addToCart(p, qty = 3)

        val item = vm.cart.value.first()
        assertEquals(3, item.qty)
    }
}
