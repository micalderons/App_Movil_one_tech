package com.example.one_teach.ui.screens.perfil

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.one_teach.navigation.Route
import com.example.one_teach.ui.components.AppScaffold
import com.example.one_teach.ui.components.BottomBar

@Composable
fun PerfilEntryScreen(nav: NavController) {
    val currentRoute = nav.currentBackStackEntryAsState().value?.destination?.route

    AppScaffold(
        nav = nav,
        tittle = "Perfil",
        bottomBar = { BottomBar(navController = nav, currentRoute = currentRoute) }
    ) { inner ->
        Column(
            modifier = inner
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {
            // 🔹 Botón para crear cuenta
            Button(
                onClick = { nav.navigate(Route.Register.path) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Crear cuenta")
            }

            // 🔹 Botón para iniciar sesión
            OutlinedButton(
                onClick = {
                    // Cuando tengas creada la pantalla de login, reemplaza esta línea por:
                    // nav.navigate(Route.Login.path)
                    // Por ahora mostramos un mensaje de placeholder
                    println("Pantalla de inicio de sesión aún no implementada")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Iniciar sesión")
            }
        }
    }
}

