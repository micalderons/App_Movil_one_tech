package com.example.one_teach.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.one_teach.ui.screens.HomeScreen
import com.example.one_teach.ui.screens.PerfilScreen
import com.example.one_teach.ui.screens.ResumenScreen
import com.example.one_teach.ui.screens.registro.RegistrationScreen
import com.example.one_teach.ui.utils.PlaceholderScreen
import com.example.one_teach.viewmodel.ConfigViewModel
import com.example.one_teach.viewmodel.PerfilViewModel
import com.example.one_teach.viewmodel.ProductoViewModel
import com.example.one_teach.viewmodel.RegistrationViewModel

@Composable
fun AppNavHost(nav: NavHostController) {
    val configVM: ConfigViewModel = viewModel()
    val perfilVM: PerfilViewModel = viewModel()
    val productoVM: ProductoViewModel = viewModel()
    val registroVM: RegistrationViewModel = viewModel()

    NavHost(navController = nav, startDestination = Route.Home.path) {
        composable(Route.Home.path) {
            HomeScreen(navController = nav, configVM = configVM)
        }
        composable(Route.Perfil.path) {
            PerfilScreen(nav = nav, vm = perfilVM)
        }
        composable(Route.Register.path) {
            RegistrationScreen(
                vm = registroVM,
                onRegisterSuccess = { _ -> nav.navigate(Route.Perfil.path) }
            )
        }
        composable(Route.Resumen.path) {
            ResumenScreen(nav = nav, vm = productoVM)
        }
        composable(Route.Buscar.path) {
            PlaceholderScreen("buscar")
        }
        composable(Route.Mas.path) {
            PlaceholderScreen("Más")
        }

    }
}
