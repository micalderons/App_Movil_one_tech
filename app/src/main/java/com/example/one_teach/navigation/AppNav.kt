package com.example.one_teach.navigation

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.one_teach.ui.screens.HomeScreen
import com.example.one_teach.ui.screens.ResumenScreen
import com.example.one_teach.ui.screens.PlaceholderScreen
import com.example.one_teach.viewmodel.*
import com.example.one_teach.ui.screens.WelcomeScreen
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.one_teach.ui.screens.AboutScreen
import com.example.one_teach.ui.screens.CartScreen
import com.example.one_teach.ui.screens.LoginScreen
import com.example.one_teach.ui.screens.PerfilEntryScreen
import com.example.one_teach.ui.screens.PoliciesScreen
import com.example.one_teach.ui.screens.ProductDetailScreen
import com.example.one_teach.ui.screens.ProfileScreen
import com.example.one_teach.ui.screens.RegistrationScreen
import com.example.one_teach.ui.screens.ReviewsScreen
import com.example.one_teach.ui.screens.SearchScreen
import com.example.one_teach.ui.screens.SettingsScreen
import com.example.one_teach.ui.utilss.PlaceholderScreen


@Composable
fun AppNavHost(nav: NavHostController) {
    val homeVM: HomeViewModel = viewModel()
    val configVM: ConfigViewModel = viewModel()
    val productoVM: ProductoViewModel = viewModel()
    val registroVM: RegistrationViewModel = viewModel()
    val profileVM: ProfileViewModel = viewModel()

    NavHost(
        navController = nav,
        startDestination = Route.Welcome.path,
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
        composable(Route.Welcome.path) {
            WelcomeScreen(
                onRegister = { nav.navigate(Route.Register.path) },
                onLogin = { nav.navigate(Route.Login.path) },
                onGuest = { nav.navigate(Route.Home.path) } // o Home
            )
        }
        composable(
            route = Route.ProductDetail.path,
            arguments = listOf(
                navArgument("id") { type = NavType.StringType }
            )
        ) { backStack ->
            val productId = backStack.arguments?.getString("id") ?: ""
            ProductDetailScreen(
                nav = nav,
                productId = productId,
                homeVM = homeVM,
                carritoVM = productoVM
            )
        }
        }
    }

