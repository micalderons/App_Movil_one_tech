package com.example.one_teach.data.dto

import java.math.BigDecimal

data class ProductDto(
    val id: Long?,
    val name: String?,
    val slug: String?,
    val description: String?,
    val price: BigDecimal?
)
