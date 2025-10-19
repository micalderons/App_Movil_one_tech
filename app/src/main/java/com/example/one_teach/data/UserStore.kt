package com.example.one_teach.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.one_teach.model.Usuario
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private const val USERS_DS_NAME = "users_prefs"
val Context.usersDataStore by preferencesDataStore(name = USERS_DS_NAME)

private val USERS_JSON = stringPreferencesKey("users_json")

private val json = Json { ignoreUnknownKeys = true; encodeDefaults = true }

class UserStore(private val context: Context) {

    val usersFlow: Flow<List<Usuario>> =
        context.usersDataStore.data.map { prefs ->
            val raw = prefs[USERS_JSON].orEmpty()
            if (raw.isBlank()) emptyList()
            else runCatching { json.decodeFromString<List<Usuario>>(raw) }.getOrElse { emptyList() }
        }

    suspend fun saveAll(users: List<Usuario>) {
        val serialized = json.encodeToString(users)
        context.usersDataStore.edit { it[USERS_JSON] = serialized }
    }
}
