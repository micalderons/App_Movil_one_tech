package com.example.one_teach.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.one_teach.ui.screens.HomeScreen
import com.example.one_teach.viewmodel.ConfigViewModel
import com.example.one_teach.viewmodel.PerfilViewModel
import com.example.one_teach.viewmodel.ProductoViewModel

@Composable
fun AppNavHost(nav: NavHostController) {
    val configVM: ConfigViewModel= viewModel()
    val perfilVM: PerfilViewModel = viewModel()
    val productoVM: ProductoViewModel = viewModel()

    NavHost(navController = nav, startDestination = Route.Home.path, route = Route.Root.path){
        composable(Route.Home.path){
            HomeScreen(nav, configVM)
        }
        composable(Route.Perfil.path){
            PerfilScreen(nav, perfilVM)
        }
        composable(Route.Registrar.path){
            ProductosScreen(nav, productoVM)
        }
        composable(Route.Resumen.path){
            ResumenScreen(nav, perfilVM)
        }
    }

})