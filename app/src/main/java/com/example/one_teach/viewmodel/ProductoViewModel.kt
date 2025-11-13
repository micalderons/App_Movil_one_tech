package com.example.one_teach.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.one_teach.model.CartItem
import com.example.one_teach.model.ProductoUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductoViewModel : ViewModel() {

    // Lista de productos cargados
    private val _productos = MutableStateFlow<List<ProductoUiState>>(emptyList())
    val productos: StateFlow<List<ProductoUiState>> = _productos

    // Carrito
    private val _cart = MutableStateFlow<List<CartItem>>(emptyList())
    val cart: StateFlow<List<CartItem>> = _cart



    //   AGREGAR AL CARRITO

    fun addToCart(product: ProductoUiState, qty: Int = 1) {
        addToCart(
            CartItem(
                id = product.id,
                name = product.name,
                price = product.price,
                qty = qty
            )
        )
    }

    fun addToCart(item: CartItem) {
        viewModelScope.launch {
            val current = _cart.value.toMutableList()
            val idx = current.indexOfFirst { it.id == item.id }

            if (idx >= 0) {
                val old = current[idx]
                current[idx] = old.copy(qty = old.qty + item.qty)
            } else {
                current.add(item)
            }

            _cart.value = current
        }
    }



    //   REMOVER DEL CARRITO

    fun removeFromCart(product: ProductoUiState) {
        removeFromCart(product.id)
    }

    fun removeFromCart(productId: String) {
        viewModelScope.launch {
            _cart.value = _cart.value.filterNot { it.id == productId }
        }
    }



    //   ACTUALIZAR CANTIDAD

    fun updateQty(productId: String, qty: Int) {
        if (qty <= 0) {
            removeFromCart(productId)
            return
        }

        viewModelScope.launch {
            _cart.value = _cart.value.map {
                if (it.id == productId) it.copy(qty = qty)
                else it
            }
        }
    }
    //LIMPIAR CARRITO
    fun clearCart() {
        viewModelScope.launch {
            _cart.value = emptyList()
        }
    }
}
