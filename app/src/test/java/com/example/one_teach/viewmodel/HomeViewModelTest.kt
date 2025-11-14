package com.example.one_teach.viewmodel

import com.example.one_teach.ui.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    // Regla que instala un dispatcher de prueba como Dispatchers.Main
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        // MUY IMPORTANTE: crear el ViewModel DESPUÉS de que la regla se aplica
        viewModel = HomeViewModel()
    }

    @Test
    fun `lista de productos inicial no esta vacia`() = runTest {
        val productos = viewModel.products.first { it.isNotEmpty() }

        assertTrue(
            "La lista de productos no debería estar vacía",
            productos.isNotEmpty()
        )
    }

    @Test
    fun `categorias coinciden con categorias distintas de los productos`() {
        val productos = viewModel.products.value

        val categoriasEsperadas = productos.map { it.category }.distinct().sorted()
        val categoriasVM = viewModel.categories.value.sorted()

        assertEquals(
            "Las categorías del ViewModel deben coincidir con las categorías distintas de los productos",
            categoriasEsperadas,
            categoriasVM
        )
    }

    @Test
    fun `todas las categorias listadas tienen al menos un producto asociado`() {
        val productos = viewModel.products.value
        val categoriasVM = viewModel.categories.value

        categoriasVM.forEach { cat ->
            assertTrue(
                "La categoría '$cat' debería tener al menos un producto asociado",
                productos.any { it.category == cat }
            )
        }
    }
}
