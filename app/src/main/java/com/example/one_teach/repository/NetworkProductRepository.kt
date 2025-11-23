package com.example.one_teach.repository

import com.example.one_teach.model.Product

class NetworkProductRepository {
    suspend fun fetchProducts(): List<Product> {
        return listOf(
            Product(1, "Producto A", 10.0),
            Product(2, "Producto B", 20.0),
            Product(3, "Producto C", 30.0)
        )
    }
}