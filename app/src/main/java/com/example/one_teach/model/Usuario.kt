package com.example.one_teach.model

import kotlinx.serialization.Serializable

@Serializable
data class Usuario(
    val fullname: String,
    val rut: String,
    val email: String,
    val phone: String,
    val direccion: String,
    val region: String,
    val comuna: String,
    val photoUri:String? =null
)

