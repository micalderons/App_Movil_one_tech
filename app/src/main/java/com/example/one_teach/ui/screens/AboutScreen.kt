package com.example.one_teach.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.one_teach.ui.components.AppScaffold
import com.example.one_teach.ui.components.BottomBar
import com.example.one_teach.ui.components.MoreMenu

@Composable
fun AboutScreen(nav: NavController) {
    val currentRoute = nav.currentBackStackEntryAsState().value?.destination?.route

    AppScaffold(
        nav = nav,
        tittle = "Sobre nosotros",
        bottomBar = { BottomBar(navController = nav, currentRoute = currentRoute) },
        actions = { MoreMenu(nav) }
    ) { modifier ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("ONE-TECH", style = MaterialTheme.typography.headlineSmall)
            Text("Somos un equipo dedicado a ofrecer productos de tecnología con una experiencia simple y clara para el usuario.")
        }
    }
}
