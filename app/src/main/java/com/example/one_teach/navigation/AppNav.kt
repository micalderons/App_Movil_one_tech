package com.example.one_teach.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.one_teach.ui.screens.HomeScreen
import com.example.one_teach.ui.screens.ProfilesScreen
import com.example.one_teach.ui.screens.ResumenScreen
import com.example.one_teach.ui.screens.registro.RegistrationScreen
import com.example.one_teach.ui.utils.PlaceholderScreen
import com.example.one_teach.viewmodel.ConfigViewModel
import com.example.one_teach.viewmodel.ProfilesViewModel
import com.example.one_teach.viewmodel.ProductoViewModel
import com.example.one_teach.viewmodel.RegistrationViewModel
import androidx.compose.runtime.getValue

@Composable
fun AppNavHost(nav: NavHostController) {
    val configVM: ConfigViewModel = viewModel()
    val perfilVM: ProfilesViewModel = viewModel()
    val productoVM: ProductoViewModel = viewModel()
    val registroVM: RegistrationViewModel = viewModel()


    NavHost(navController = nav, startDestination = Route.Home.path) {
        composable(Route.Home.path) {
            HomeScreen(navController = nav, configVM = configVM)
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
        composable(Route.Perfiles.path) {
            ProfilesScreen(nav)
        }
        composable(Route.Register.path) {
            RegistrationScreen(navController = nav, vm = registroVM)
        }
        composable(Route.Perfil.path) {
            val vm: ProfilesViewModel = viewModel()
            val users by vm.users.collectAsState()


            LaunchedEffect(users) {
                if (users.isEmpty()) {
                    nav.navigate(Route.Register.path) {

                        popUpTo(Route.Perfil.path) { inclusive = true }
                    }
                }
            }

            if (users.isNotEmpty()) {
                ProfilesScreen(nav)
            }
        }
    }
}

