package com.example.one_teach.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.one_teach.navigation.Route

@Composable
fun MoreMenu(nav: NavController, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }, modifier = modifier) {
        Icon(Icons.Filled.MoreVert, contentDescription = "Menú")
    }
    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
        DropdownMenuItem(
            text = { Text("Configuraciones") },
            onClick = {
                expanded = false
                nav.navigate(Route.Settings.path)
            }
        )
        DropdownMenuItem(
            text = { Text("Reseñas") },
            onClick = {
                expanded = false
                nav.navigate(Route.Reviews.path)
            }
        )
        DropdownMenuItem(
            text = { Text("Sobre nosotros") },
            onClick = {
                expanded = false
                nav.navigate(Route.About.path)
            }
        )
        DropdownMenuItem(
            text = { Text("Políticas y derechos reservados") },
            onClick = {
                expanded = false
                nav.navigate(Route.Policies.path)
            }
        )
    }
}
