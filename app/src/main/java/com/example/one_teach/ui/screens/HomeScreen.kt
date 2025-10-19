@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.one_teach.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.one_teach.navigation.Route
import com.example.one_teach.ui.components.AppScaffold
import com.example.one_teach.ui.components.BottomBar
import com.example.one_teach.ui.components.ProductCard
import com.example.one_teach.viewmodel.ConfigViewModel
import com.example.one_teach.viewmodel.HomeViewModel
import com.example.one_teach.viewmodel.ProductoViewModel
import kotlinx.coroutines.launch


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

    // Snackbar
    val snackbarHost = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Estado del bottom sheet (producto seleccionado y visibilidad)
    var selectedProductId by remember { mutableStateOf<String?>(null) }
    val selectedProduct = products.firstOrNull { it.id == selectedProductId }

    AppScaffold(
        nav = navController,
        tittle = "ONE-TECH",
        snackbarHostState = snackbarHost,
        bottomBar = {
            BottomBar(
                navController = navController,
                currentRoute = Route.Home.path
            )
        }
    ) { innerModifier ->

        Column(
            modifier = innerModifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (modo) {
                Text(
                    text = "Modo vendedor activo",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            if (categories.isEmpty() || products.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay productos para mostrar",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                return@Column
            }

            LazyColumn(contentPadding = PaddingValues(vertical = 8.dp)) {
                items(categories, key = { it }) { category ->
                    val productForCategory = products.filter { it.category == category }
                    if (productForCategory.isNotEmpty()) {
                        Column(modifier = Modifier.padding(vertical = 8.dp)) {
                            Text(
                                text = category,
                                style = MaterialTheme.typography.titleMedium,
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
                                        onClick = { selectedProductId = product.id }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        selectedProduct?.let { product ->
            val inCart = cart.any { it.id == product.id }

            ModalBottomSheet(
                onDismissRequest = { selectedProductId = null },
                // dragHandle = { SheetDragHandle() }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Categoría: ${product.category}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "Precio: ${product.price} CLP",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(Modifier.height(8.dp))

                    // Agregar
                    Button(
                        onClick = {
                            carritoVM.addToCart(product) // overload agregado en tu VM
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

                    Spacer(Modifier.height(12.dp))
                }
            }
        }
    }
}
