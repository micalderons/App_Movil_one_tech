package com.example.one_teach.viewmodel
import android.app.Application
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

private val Application.datastore by preferencesDataStore("app.prefs")
private val KEY_MODO = booleanPreferencesKey("modo_vendedor")

class ConfigViewModel(app: Application) : AndroidViewModel(app) {
    val modoVendedor : StateFlow<Boolean> =
        app.datastore.data.map{it[KEY_MODO]?:false}.stateIn(
            viewModelScope, SharingStarted.Eagerly, false
        )

    fun toggleModo() = viewModelScope.launch {
        getApplication<Application>().datastore.edit{ prefs ->
            prefs[KEY_MODO] = !(modoVendedor.value)
        }
    }
}