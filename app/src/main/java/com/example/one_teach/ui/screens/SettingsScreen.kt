package com.example.one_teach.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.one_teach.ui.components.AppScaffold
import com.example.one_teach.ui.components.BottomBar
import com.example.one_teach.ui.components.MoreMenu

@Composable
fun SettingsScreen(nav: NavController) {
    val currentRoute = nav.currentBackStackEntryAsState().value?.destination?.route

    AppScaffold(
        nav = nav,
        tittle = "Configuraciones",
        bottomBar = { BottomBar(navController = nav, currentRoute = currentRoute) },
        actions = { MoreMenu(nav) }
    ) { modifier ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Preferencias", style = MaterialTheme.typography.headlineSmall)
            Text("Aquí puedes colocar temas, notificaciones, idioma, etc.")
        }
    }
}
