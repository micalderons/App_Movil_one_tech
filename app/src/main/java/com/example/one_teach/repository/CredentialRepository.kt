package com.example.one_teach.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import java.security.MessageDigest
import java.util.Locale

private val Context.dataStore by preferencesDataStore("creds_store")

class CredentialsRepository(private val context: Context) {

    // guardamos hash por email. key = "pwd_<email>"
    private fun pwdKey(email: String) =
        stringPreferencesKey("pwd_${email.lowercase(Locale.ROOT)}")

    private val rememberKey = booleanPreferencesKey("remember_me")
    private val lastUserKey  = stringPreferencesKey("last_user_email")

    suspend fun savePassword(email: String, plain: String) {
        val hash = sha256(plain)
        context.dataStore.edit { it[pwdKey(email)] = hash }
    }

    suspend fun checkPassword(email: String, plain: String): Boolean {
        val prefs = context.dataStore.data.first()
        val stored = prefs[pwdKey(email)] ?: return false
        return stored == sha256(plain)
    }

    suspend fun setRememberMe(enabled: Boolean, email: String) {
        context.dataStore.edit {
            it[rememberKey] = enabled
            it[lastUserKey] = email
        }
    }

    suspend fun isRemembered(): Boolean =
        context.dataStore.data.first()[rememberKey] ?: false

    suspend fun lastUserEmail(): String? =
        context.dataStore.data.first()[lastUserKey]

    private fun sha256(txt: String): String {
        val d = MessageDigest.getInstance("SHA-256")
        val bytes = d.digest(txt.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
