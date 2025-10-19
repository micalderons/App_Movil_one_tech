package com.example.one_teach.viewmodel

import androidx.lifecycle.ViewModel
import com.example.one_teach.model.Usuario
import com.example.one_teach.ui.utils.*

// 👇 IMPORTS para que Compose observe el estado
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class RegistrationUiState(
    val fullName: String = "",
    val rut: String = "",
    val email: String = "",
    val phone: String = "",
    val direccion: String = "",
    val region: String = "",
    val comuna: String = "",
    val password: String = "",
    val confirmPassword: String = "",

    val acceptedTerms: Boolean = false,

    val fullNameError: String? = null,
    val rutError: String? = null,
    val emailError: String? = null,
    val phoneError: String? = null,
    val addressError: String? = null,
    val regionError: String? = null,
    val comunaError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val termsError: String? = null,

    val canSubmit: Boolean = false
)

class RegistrationViewModel : ViewModel() {

    var ui by mutableStateOf(RegistrationUiState())
        private set

    fun onFullNameChange(v: String)       = update { copy(fullName = v, fullNameError = null) }
    fun onRutChange(v: String)             = update { copy(rut = v, rutError = null) }
    fun onEmailChange(v: String)           = update { copy(email = v, emailError = null) }
    fun onPhoneChange(v: String)           = update { copy(phone = v, phoneError = null) }
    fun onAddressChange(v: String)         = update { copy(direccion = v, addressError = null) }
    fun onRegionChange(v: String)          = update { copy(region = v, regionError = null) }
    fun onComunaChange(v: String)          = update { copy(comuna = v, comunaError = null) }
    fun onPasswordChange(v: String)        = update { copy(password = v, passwordError = null) }
    fun onConfirmPasswordChange(v: String) = update { copy(confirmPassword = v, confirmPasswordError = null) }
    fun onToggleTerms(v: Boolean)          = update { copy(acceptedTerms = v, termsError = null) }

    private fun update(block: RegistrationUiState.() -> RegistrationUiState) {
        ui = block(ui).recompute() // reasignar dispara recomposición
    }

    private fun RegistrationUiState.recompute(): RegistrationUiState {
        val okName    = fullName.isNotBlank()
        val okRut     = isValidRut(rut)
        val okEmail   = isValidEmail(email)
        val okPhone   = isValidPhone(phone)
        val okAddress = direccion.isNotBlank()
        val okRegion  = region.isNotBlank()
        val okComuna  = comuna.isNotBlank()
        val okPwd     = isStrongEnoughPassword(password)
        val okConfirm = confirmPassword == password && password.isNotBlank()
        val okTerms   = acceptedTerms

        return copy(
            canSubmit = okName && okRut && okEmail && okPhone &&
                    okAddress && okRegion && okComuna && okPwd &&
                    okConfirm && okTerms
        )
    }

    fun submitOrErrors(): Usuario? {
        var nameErr: String? = null
        var rutErr: String? = null
        var emailErr: String? = null
        var phoneErr: String? = null
        var addressErr: String? = null
        var regionErr: String? = null
        var comunaErr: String? = null
        var pwdErr: String? = null
        var confirmErr: String? = null
        var termsErr: String? = null

        if (ui.fullName.isBlank())                nameErr    = "Ingrese su nombre completo"
        if (!isValidRut(ui.rut))                  rutErr     = "RUT inválido (verifique DV)"
        if (!isValidEmail(ui.email))              emailErr   = "Correo inválido"
        if (!isValidPhone(ui.phone))              phoneErr   = "Teléfono inválido"
        if (ui.direccion.isBlank())               addressErr = "Ingrese dirección"
        if (ui.region.isBlank())                  regionErr  = "Ingrese región"
        if (ui.comuna.isBlank())                  comunaErr  = "Ingrese comuna"
        if (!isStrongEnoughPassword(ui.password)) pwdErr     = "Mínimo 6 caracteres"
        if (ui.confirmPassword != ui.password)    confirmErr = "Las contraseñas no coinciden"
        if (!ui.acceptedTerms)                    termsErr   = "Debes aceptar los términos y condiciones"

        ui = ui.copy(
            fullNameError = nameErr,
            rutError = rutErr,
            emailError = emailErr,
            phoneError = phoneErr,
            addressError = addressErr,
            regionError = regionErr,
            comunaError = comunaErr,
            passwordError = pwdErr,
            confirmPasswordError = confirmErr,
            termsError = termsErr
        ).recompute()

        if (ui.canSubmit) {
            return Usuario(
                fullname  = ui.fullName.trim(),
                rut       = formatRut(ui.rut),
                email     = ui.email.trim(),
                phone     = ui.phone.trim(),
                direccion = ui.direccion.trim(),
                region    = ui.region.trim(),
                comuna    = ui.comuna.trim()
            )
        }
        return null
    }
}
