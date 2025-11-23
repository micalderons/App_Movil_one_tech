@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.one_teach.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.one_teach.navigation.Route
import com.example.one_teach.ui.components.AppScaffold
import com.example.one_teach.ui.components.BottomBar
import com.example.one_teach.ui.components.MoreMenu
import com.example.one_teach.ui.components.ProductCard
import com.example.one_teach.viewmodel.ConfigViewModel
import com.example.one_teach.viewmodel.HomeViewModel
import com.example.one_teach.viewmodel.ProductoViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    navController: NavController,
    configVM: ConfigViewModel = viewModel(),
    homeViewModel: HomeViewModel = viewModel(),
    carritoVM: ProductoViewModel
) {
    val products by homeViewModel.products.collectAsState(initial = emptyList())
    val categories by homeViewModel.categories.collectAsState(initial = emptyList())
    val modo by configVM.modoVendedor.collectAsState(initial = false)
    val cart by carritoVM.cart.collectAsState()

    val snackbarHost = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val cs = MaterialTheme.colorScheme
    val pill = RoundedCornerShape(24.dp)

    var selectedProductId by rememberSaveable { mutableStateOf<String?>(null) }
    val selectedProduct by remember(products, selectedProductId) {
        mutableStateOf(products.firstOrNull { it.id == selectedProductId })
    }

    var isLoading by rememberSaveable { mutableStateOf(false) }
    var pendingProductId by rememberSaveable { mutableStateOf<String?>(null) }

    LaunchedEffect(pendingProductId) {
        val id = pendingProductId ?: return@LaunchedEffect
        isLoading = true
        delay(600)
        navController.navigate(Route.ProductDetail.build(id))
        isLoading = false
        pendingProductId = null
    }

    AppScaffold(
        nav = navController,
        tittle = "ONE-TECH",
        snackbarHostState = snackbarHost,
        bottomBar = { BottomBar(navController, Route.Home.path) },
        actions = { MoreMenu(navController) }
    ) { innerModifier ->

        Box(
            modifier = innerModifier
                .fillMaxSize()
                .background(cs.background)
        ) {

            Column(modifier = Modifier.fillMaxSize()) {

                if (modo) {
                    Text(
                        text = "Modo vendedor activo",
                        color = cs.primary,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                if (categories.isEmpty() || products.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No hay productos para mostrar", style = MaterialTheme.typography.bodyLarge)
                    }
                } else {
                    LazyColumn(contentPadding = PaddingValues(vertical = 8.dp)) {
                        items(categories, key = { it }) { category ->
                            val productForCategory = products.filter { it.category == category }
                            if (productForCategory.isNotEmpty()) {
                                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                                    Text(
                                        text = category,
                                        style = MaterialTheme.typography.titleMedium,
                                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                        color = cs.onBackground
                                    )
                                    LazyRow(
                                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                                    ) {
                                        items(productForCategory, key = { it.id }) { product ->
                                            ProductCard(
                                                modifier = Modifier.width(300.dp),
                                                product = product,
                                                onClick = { selectedProductId = product.id }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            selectedProduct?.let { product ->
                val inCart by remember(cart) {
                    derivedStateOf { cart.any { it.id == product.id } }
                }

                ModalBottomSheet(
                    onDismissRequest = { selectedProductId = null }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(product.name, style = MaterialTheme.typography.titleMedium)
                        Text("Categoría: ${product.category}", style = MaterialTheme.typography.bodySmall)
                        Text("Precio: ${product.price} CLP", style = MaterialTheme.typography.bodyMedium)

                        OutlinedButton(
                            onClick = {
                                selectedProductId = null
                                pendingProductId = product.id
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            shape = pill,
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = cs.onSurface)
                        ) { Text("Ver producto") }

                        Button(
                            onClick = {
                                carritoVM.addToCart(product)
                                selectedProductId = null
                                scope.launch { snackbarHost.showSnackbar("Agregado al carrito") }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) { Text("Agregar al carrito") }

                        if (inCart) {
                            OutlinedButton(
                                onClick = {
                                    carritoVM.removeFromCart(product.id)
                                    selectedProductId = null
                                    scope.launch { snackbarHost.showSnackbar("Quitado del carrito") }
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) { Text("Quitar del carrito") }
                        }

                        TextButton(
                            onClick = { selectedProductId = null },
                            modifier = Modifier.fillMaxWidth()
                        ) { Text("Cancelar") }
                    }
                }
            }

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(cs.surface.copy(alpha = 0.4f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
