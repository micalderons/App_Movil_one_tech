package com.example.one_teach.repository

import android.content.Context
import com.example.one_teach.data.UserStore
import com.example.one_teach.model.Usuario
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class UserRepository(context: Context) {
    private val store = UserStore(context)

    val users: Flow<List<Usuario>> = store.usersFlow

    suspend fun addUser(newUser: Usuario) {
        val current = store.usersFlow.first()
        store.saveAll(current + newUser)
    }

    suspend fun removeUser(email: String) {
        val current = store.usersFlow.first()
        store.saveAll(current.filterNot { it.email.equals(email, ignoreCase = true) })
    }

    suspend fun clear() {
        store.saveAll(emptyList())
    }
    suspend fun updatePhoto(email: String, newUri: String?) {
        val current = store.usersFlow.first()
        store.saveAll(
            current.map { u ->
                if (u.email.equals(email, ignoreCase = true)) u.copy(photoUri = newUri)
                else u
            }
        )
    }



}
