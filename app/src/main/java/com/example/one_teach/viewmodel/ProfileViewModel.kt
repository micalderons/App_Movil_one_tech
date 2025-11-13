package com.example.one_teach.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.one_teach.model.Usuario
import com.example.one_teach.viewmodel.ProfileUi


data class ProfileUi(
    val fullname: String = "",
    val rut: String = "",
    val email: String = "",
    val phone: String = "",
    val direccion: String = "",
    val region: String = "",
    val comuna: String = "",
    val photoUri: String? = null,
    val editing: Boolean = false
)

class ProfileViewModel : ViewModel() {


    var ui by mutableStateOf(ProfileUi())
        private set


    private val _users = MutableStateFlow<List<Usuario>>(emptyList())
    val users: StateFlow<List<Usuario>> = _users.asStateFlow()


    fun loadFromUser(u: Usuario) {
        ui = ui.copy(
            fullname = u.fullname,
            rut = u.rut,
            email = u.email,
            phone = u.phone,
            direccion = u.direccion,
            region = u.region,
            comuna = u.comuna
        )
    }


    fun addOrSelectUser(u: Usuario) {
        val current = _users.value.toMutableList()
        val idx = current.indexOfFirst { it.email.equals(u.email, ignoreCase = true) }
        if (idx >= 0) current[idx] = u else current.add(u)
        _users.value = current
        loadFromUser(u)
    }


    fun setEditing(on: Boolean) { ui = ui.copy(editing = on) }

    fun updateFullname(v: String) { ui = ui.copy(fullname = v) }
    fun updatePhone(v: String)    { ui = ui.copy(phone = v) }
    fun updateDireccion(v: String){ ui = ui.copy(direccion = v) }
    fun updateRegion(v: String)   { ui = ui.copy(region = v) }
    fun updateComuna(v: String)   { ui = ui.copy(comuna = v) }

    fun updatePhoto(uri: String)  { ui = ui.copy(photoUri = uri) } // solo UI


    fun saveChanges() {
        val emailKey = ui.email
        if (emailKey.isNotBlank()) {
            _users.value = _users.value.map { u ->
                if (u.email.equals(emailKey, ignoreCase = true)) {
                    u.copy(
                        fullname  = ui.fullname,
                        phone     = ui.phone,
                        direccion = ui.direccion,
                        region    = ui.region,
                        comuna    = ui.comuna
                        // rut y email no los tocamos aquí
                    )
                } else u
            }
        }
        ui = ui.copy(editing = false)
    }


    fun clearUsers() {
        _users.value = emptyList()
        ui = ProfileUi()
    }
}
