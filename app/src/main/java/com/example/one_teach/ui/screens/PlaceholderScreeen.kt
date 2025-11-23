package com.example.one_teach.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.one_teach.ui.components.AppScaffold
import com.example.one_teach.ui.components.BottomBar

@Composable
fun PlaceholderScreen(
    nav: NavController,
    tittle: String,
    message: String = "Próximamente…"
) {
    val currentRoute = nav.currentBackStackEntryAsState().value?.destination?.route

    AppScaffold(
        nav = nav,
        tittle = tittle,
        bottomBar = { BottomBar(navController = nav, currentRoute = currentRoute) }
    ) { modifier ->
        Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(message, style = MaterialTheme.typography.titleMedium)
        }
    }
}
