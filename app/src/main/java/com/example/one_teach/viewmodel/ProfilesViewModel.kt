package com.example.one_teach.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.one_teach.model.Usuario
import com.example.one_teach.repository.UserRepository
import com.example.one_teach.ui.utils.isValidEmail
import com.example.one_teach.ui.utils.isValidRut
import com.example.one_teach.ui.utils.formatRut
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ProfileFormState(
    val fullname: String = "",
    val rut: String = "",
    val email: String = "",
    val phone: String = "",
    val direccion: String = "",
    val region: String = "",
    val comuna: String = "",

    val fullnameError: String? = null,
    val rutError: String? = null,
    val emailError: String? = null,
    val phoneError: String? = null,
    val direccionError: String? = null,
    val regionError: String? = null,
    val comunaError: String? = null,

    val canSave: Boolean = false
)

class ProfilesViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = UserRepository(app)

    val users: StateFlow<List<Usuario>> =
        repo.users.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    var form = ProfileFormState()
        private set

    fun onFullname(v: String) { form = form.copy(fullname = v, fullnameError = null).recompute() }
    fun onRut(v: String)      { form = form.copy(rut = v.uppercase(), rutError = null).recompute() }
    fun onEmail(v: String)    { form = form.copy(email = v, emailError = null).recompute() }
    fun onPhone(v: String)    { form = form.copy(phone = v.filter { it.isDigit() }, phoneError = null).recompute() }
    fun onDireccion(v: String){ form = form.copy(direccion = v, direccionError = null).recompute() }
    fun onRegion(v: String)   { form = form.copy(region = v, regionError = null).recompute() }
    fun onComuna(v: String)   { form = form.copy(comuna = v, comunaError = null).recompute() }

    private fun ProfileFormState.recompute(): ProfileFormState {
        val okName = fullname.isNotBlank()
        val okRut  = isValidRut(rut)
        val okMail = isValidEmail(email)
        val okPh   = phone.length in 9..12
        val okDir  = direccion.isNotBlank()
        val okReg  = region.isNotBlank()
        val okCom  = comuna.isNotBlank()

        return copy(canSave = okName && okRut && okMail && okPh && okDir && okReg && okCom)
    }

    fun saveUser(onSaved: (Usuario) -> Unit = {}) {
        // Setear mensajes de error si corresponde
        var nameErr: String? = null
        var rutErr: String? = null
        var emailErr: String? = null
        var phoneErr: String? = null
        var dirErr: String? = null
        var regErr: String? = null
        var comErr: String? = null

        if (form.fullname.isBlank()) nameErr = "Ingrese su nombre"
        if (!isValidRut(form.rut))  rutErr  = "RUT inválido"
        if (!isValidEmail(form.email)) emailErr = "Correo inválido"
        if (form.phone.length !in 9..12) phoneErr = "Teléfono inválido"
        if (form.direccion.isBlank()) dirErr = "Ingrese dirección"
        if (form.region.isBlank()) regErr = "Ingrese región"
        if (form.comuna.isBlank()) comErr = "Ingrese comuna"

        form = form.copy(
            fullnameError = nameErr,
            rutError = rutErr,
            emailError = emailErr,
            phoneError = phoneErr,
            direccionError = dirErr,
            regionError = regErr,
            comunaError = comErr
        ).recompute()

        if (!form.canSave) return

        val u = Usuario(
            fullname = form.fullname.trim(),
            rut = formatRut(form.rut),
            email = form.email.trim(),
            phone = form.phone.trim(),
            direccion = form.direccion.trim(),
            region = form.region.trim(),
            comuna = form.comuna.trim()
        )

        viewModelScope.launch {
            repo.addUser(u)
            onSaved(u)
            form = ProfileFormState() // limpiar el formulario
        }
    }

    fun deleteUser(email: String) = viewModelScope.launch { repo.removeUser(email) }
    fun clearAll() = viewModelScope.launch { repo.clear() }
    fun updatePhoto(email: String, newUri: String?) {
        viewModelScope.launch {
            repo.updatePhoto(email, newUri)
        }
    }
}
