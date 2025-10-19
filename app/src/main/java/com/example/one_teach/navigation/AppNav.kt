package com.example.one_teach.navigation

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.one_teach.ui.screens.HomeScreen
import com.example.one_teach.ui.screens.ResumenScreen
import com.example.one_teach.ui.screens.cart.CartScreen
import com.example.one_teach.ui.screens.login.LoginScreen
import com.example.one_teach.ui.screens.perfil.PerfilEntryScreen
import com.example.one_teach.ui.screens.profile.ProfileScreen
import com.example.one_teach.ui.screens.registro.RegistrationScreen
import com.example.one_teach.ui.screens.search.SearchScreen
import com.example.one_teach.ui.utils.PlaceholderScreen
import com.example.one_teach.viewmodel.*
import com.example.one_teach.ui.screens.reviews.ReviewsScreen
import com.example.one_teach.ui.screens.policies.PoliciesScreen
import com.example.one_teach.ui.screens.settings.SettingsScreen
import com.example.one_teach.ui.screens.about.AboutScreen


@Composable
fun AppNavHost(nav: NavHostController) {
    val homeVM: HomeViewModel = viewModel()
    val configVM: ConfigViewModel = viewModel()
    val productoVM: ProductoViewModel = viewModel()
    val registroVM: RegistrationViewModel = viewModel()
    val profileVM: ProfileViewModel = viewModel()

    NavHost(
        navController = nav,
        startDestination = Route.Home.path,
        route = Route.Root.path
    ) {
        composable(Route.Home.path) {
            HomeScreen(
                navController = nav,
                configVM = configVM,
                homeViewModel = homeVM,
                carritoVM = productoVM
            )
        }

        composable(Route.Buscar.path) {
            SearchScreen(
                nav = nav,
                homeVM = homeVM,
                carritoVM = productoVM
            )
        }

        composable(Route.Resumen.path) {
            ResumenScreen(nav = nav, vm = productoVM)
        }

        composable(Route.Mas.path) { PlaceholderScreen("Más") }

        // PASA EL MISMO profileVM A REGISTRO
        composable(Route.Register.path) {
            RegistrationScreen(
                navController = nav,
                vm = registroVM,
                profileVM = profileVM
            )
        }

        composable(Route.Login.path) { LoginScreen(nav) }

        // PERFIL: decide según users del MISMO profileVM
        composable(Route.Perfil.path) {
            val users by profileVM.users.collectAsState(initial = emptyList())
            // Debug opcional:
            // LaunchedEffect(users) { android.util.Log.d("NAV", "users.size=${users.size}") }

            if (users.isEmpty()) {
                PerfilEntryScreen(nav)
            } else {
                ProfileScreen(nav, profileVM)
            }
        }
        composable(Route.Resumen.path){
            CartScreen(nav = nav, vm = productoVM)
        }
        composable(Route.Reviews.path) {
            ReviewsScreen(nav = nav, homeVM = homeVM)
        }
        composable(Route.Policies.path) {
            PoliciesScreen(nav = nav)
        }
        composable(Route.Settings.path) {
            SettingsScreen(nav = nav)
        }
        composable(Route.About.path) {
            AboutScreen(nav = nav)
        }
        composable(Route.Mas.path){
            PlaceholderScreen(
                nav=nav,
                tittle = "Más",
                message = "Más próximamente"
            )
        }
    }
}
