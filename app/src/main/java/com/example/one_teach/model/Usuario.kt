package com.example.one_teach.model

data class Usuario(
    val fullname: String,
    val rut: String,
    val email: String,
    val phone: String,
    val direccion: String,
    val region: String,
    val comuna: String
)