package com.example.one_teach.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.one_teach.viewmodel.ConfigViewModel
import com.example.one_teach.viewmodel.HomeViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.text.font.FontWeight
import com.example.one_teach.navigation.AppScreens
import com.example.one_teach.navigation.Route
import com.example.one_teach.ui.components.AppScaffold
import com.example.one_teach.ui.components.BottomBar
import com.example.one_teach.ui.components.ProductCard
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.runtime.getValue
import com.example.one_teach.viewmodel.ProfilesViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    configVM: ConfigViewModel = viewModel(),
    homeViewModel: HomeViewModel = viewModel()
) {
    val products by homeViewModel.products.collectAsState(initial = emptyList())
    val categories by homeViewModel.categories.collectAsState(initial = emptyList())
    val selectedCategory by homeViewModel.selectedCategory.collectAsState()
    val modo by configVM.modoVendedor.collectAsState(initial = false)
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val hasUser by viewModel<ProfilesViewModel>().users.collectAsState(initial = emptyList())
    val profilesVM: ProfilesViewModel = viewModel()
    val users by profilesVM.users.collectAsState()

    AppScaffold(
        nav = navController,
        tittle = "ONE-TECH",
        actions = {
            if (users.isEmpty()) {
                TextButton(onClick = { navController.navigate(Route.Register.path) }) {
                    Text("Registrarte / Iniciar sesión",
                        color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        },
        bottomBar = { BottomBar(navController = navController, currentRoute = currentRoute) }
    ) { inner ->
        TopAppBar(
            title = { Text("ONE-TECH") },
            actions = {
                if (hasUser.isEmpty()) {
                    TextButton(onClick = { navController.navigate(Route.Register.path) }) {
                        Text("Registrarte / Iniciar sesión", color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary
            )
        )

        Column(
            modifier = inner
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (modo) {
                Text(
                    "Modo vendedor activo",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            // Fallback visible si no hay categorías o productos
            val totalItems = products.size
            if (categories.isEmpty() || totalItems == 0) {
                Text(
                    "No hay productos para mostrar",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                )
                return@Column
            }

            LazyColumn(contentPadding = PaddingValues(vertical = 8.dp)) {
                items(categories, key = { it }) { category ->
                    val productForCategory = products.filter { it.category == category }
                    if (productForCategory.isNotEmpty()) {
                        Column(modifier = Modifier.padding(vertical = 8.dp)) {
                            Text(
                                text = category,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            LazyRow(
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                items(productForCategory, key = { it.id }) { product ->
                                    ProductCard(
                                        modifier = Modifier.width(300.dp),
                                        product = product,
                                        onClick = {
                                            navController.navigate(
                                                AppScreens.ProductDetailScreen.createRoute(product.id)
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        LaunchedEffect(products, categories) {
            android.util.Log.d("HOME", "cats=${categories.size}, prods=${products.size}")
        }

    }
}
