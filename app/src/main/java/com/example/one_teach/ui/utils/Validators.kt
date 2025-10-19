package com.example.one_teach.ui.utils

import android.util.Patterns
import java.util.Locale

/** -------------------- RUT -------------------- **/
fun isValidRut(input: String): Boolean {
    val raw = input.replace(".", "").replace("-", "").trim().uppercase(Locale.ROOT)
    if (raw.length < 2) return false
    val body = raw.dropLast(1)
    val dv = raw.last()
    if (!body.all { it.isDigit() }) return false

    var sum = 0
    var mul = 2
    for (c in body.reversed()) {
        sum += (c.code - 48) * mul
        mul = if (mul == 7) 2 else mul + 1
    }
    val exp = 11 - (sum % 11)
    val expDv = when (exp) {
        11 -> '0'
        10 -> 'K'
        else -> (exp + 48).toChar()
    }
    return dv == expDv
}

fun formatRut(input: String): String {
    val digits = input.replace(".", "").replace("-", "").uppercase(Locale.ROOT)
    if (digits.length < 2) return input
    val body = digits.dropLast(1)
    val dv = digits.last()
    val sb = StringBuilder()
    var c = 0
    for (i in body.length - 1 downTo 0) {
        sb.append(body[i]); c++
        if (c == 3 && i != 0) { sb.append("."); c = 0 }
    }
    return sb.reverse().append("-").append(dv).toString()
}

/** -------------------- EMAIL -------------------- **/
fun isValidEmail(email: String): Boolean =
    email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()

/** -------------------- TELÉFONO -------------------- **/
fun isValidPhone(phone: String): Boolean {
    val digits = phone.filter { it.isDigit() }
    return digits.length in 9..12
}

/** -------------------- PASSWORD -------------------- **/
fun isStrongEnoughPassword(password: String): Boolean {
    // Reglas simples: mínimo 6 caracteres. (Puedes endurecerlo si quieres)
    return password.length >= 6
    // Ejemplo más estricto:
    // return password.length >= 8 &&
    //        password.any { it.isDigit() } &&
    //        password.any { it.isLetter() }
}
