package com.example.one_teach.data.dao

import com.example.one_teach.data.dto.ProductDto
import retrofit2.http.GET

interface ApiService {
    @GET("api/products")
    suspend fun getProducts(): List<ProductDto>
}