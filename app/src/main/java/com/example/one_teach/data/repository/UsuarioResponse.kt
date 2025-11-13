package com.example.one_teach.data.repository
import java.time.LocalTime
import java.time.LocalDateTime
data class UsuarioResponse (
    val id: Long,
    val rut: String,
    val email: String,
    val phone: String,
    val direccion: String,
    val comuna: String,
    val region: String,
    val profileImageBase64: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime

)