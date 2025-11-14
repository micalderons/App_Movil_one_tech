data class DiscountResult(
    val discountAmount: Int,
    val finalTotal: Int
)

fun applyDiscountCode(subtotal: Int, code: String?): DiscountResult {
    val isValid = code?.trim()?.lowercase() == "duocuc.cl"
    val discount = if (isValid) (subtotal * 0.10).toInt() else 0
    return DiscountResult(
        discountAmount = discount,
        finalTotal = subtotal - discount
    )
}
