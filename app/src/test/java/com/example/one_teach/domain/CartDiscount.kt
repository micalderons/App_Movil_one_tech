package com.example.one_teach.domain

data class CartDiscountResult(
    val discountApplied: Boolean,
    val discountAmount: Int,
    val finalTotal: Int
)

fun applyDiscountCode(subtotal: Int, code: String): CartDiscountResult {
    val valid = code.lowercase().trim() == "duocuc.cl"
    val discount = if (valid) (subtotal * 0.10).toInt() else 0
    val total = subtotal - discount
    return CartDiscountResult(
        discountApplied = valid,
        discountAmount = discount,
        finalTotal = total
    )
}
