package com.example.one_teach.model
data class CartItem(
    val id: String,
    val name: String,
    val price: Int,
    val qty: Int
) {
    val subtotal: Int get() = price * qty
}
