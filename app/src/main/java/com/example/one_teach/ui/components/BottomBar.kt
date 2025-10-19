package com.example.one_teach.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.one_teach.navigation.Route
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Menu

@Composable
fun BottomBar(navController: NavController, currentRoute: String?) {

    NavigationBar(
        containerColor = Color(0xFF283593), // tu azul principal
        tonalElevation = 4.dp
    ) {

        NavigationBarItem(
            selected = currentRoute == Route.Home.path,
            onClick = {
                navController.navigate(Route.Home.path) {
                    launchSingleTop = true
                    popUpTo(Route.Home.path) { inclusive = false }
                }
            },
            icon = { Icon(Icons.Filled.Home, contentDescription = "Inicio") },
            label = { Text("Inicio") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = Color.White.copy(alpha = 0.7f),
                indicatorColor = Color(0xFF1A237E)
            )
        )


        NavigationBarItem(
            selected = currentRoute == Route.Buscar.path,
            onClick = { navController.navigate(Route.Buscar.path) },
            icon = { Icon(Icons.Filled.Search, contentDescription = "Buscar") },
            label = { Text("Buscar") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = Color.White.copy(alpha = 0.7f),
                indicatorColor = Color(0xFF1A237E)
            )
        )


        NavigationBarItem(
            selected = currentRoute == Route.Resumen.path,
            onClick = { /* TODO */ },
            icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Carrito") },
            label = { Text("Carrito") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = Color.White.copy(alpha = 0.7f),
                indicatorColor = Color(0xFF1A237E)
            )
        )


        NavigationBarItem(
            selected = currentRoute == Route.Perfil.path,
            onClick = {

                navController.navigate(Route.Perfil.path) {
                    launchSingleTop = true
                }
            },
            icon = { Icon(Icons.Filled.Person, contentDescription = "Perfil") },
            label = { Text("Perfil") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = Color.White.copy(alpha = 0.7f),
                indicatorColor = Color(0xFF1A237E)
            )
        )


        NavigationBarItem(
            selected = currentRoute == Route.Mas.path,
            onClick = { /* TODO */ },
            icon = { Icon(Icons.Filled.Menu, contentDescription = "Más") },
            label = { Text("Más") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = Color.White.copy(alpha = 0.7f),
                indicatorColor = Color(0xFF1A237E)
            )
        )


    }
}
