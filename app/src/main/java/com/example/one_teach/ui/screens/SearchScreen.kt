@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.one_teach.ui.screens.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.one_teach.navigation.Route
import com.example.one_teach.ui.components.AppScaffold
import com.example.one_teach.ui.components.BottomBar
import com.example.one_teach.viewmodel.HomeViewModel
import com.example.one_teach.viewmodel.ProductoViewModel

@Composable
fun SearchScreen(
    nav: NavController,
    homeVM: HomeViewModel,
    carritoVM: ProductoViewModel
) {
    val query by homeVM.searchQuery.collectAsState()
    val selectedCategory by homeVM.selectedCategory.collectAsState()
    val categories by homeVM.categoriesForUi.collectAsState()
    val products by homeVM.filteredProducts.collectAsState()

    AppScaffold(
        nav = nav,
        tittle = "Buscar",
        bottomBar = {
            BottomBar(
                navController = nav,
                currentRoute = Route.Buscar.path      // <- marca el tab “Buscar”
            )
        }
    ) { innerMod ->

        Column(
            modifier = innerMod
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("Buscar productos", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(8.dp))


            OutlinedTextField(
                value = query,
                onValueChange = homeVM::onSearchQueryChange,
                label = { Text("Buscar por nombre") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(Modifier.height(12.dp))


            var expanded by remember { mutableStateOf(false) }
            Box {
                OutlinedButton(
                    onClick = { expanded = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(selectedCategory)
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categories.forEach { cat ->
                        DropdownMenuItem(
                            text = { Text(cat) },
                            onClick = {
                                homeVM.onCategorySelected(cat)
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))


            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(products, key = { it.id }) { product ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Text(product.name, style = MaterialTheme.typography.titleMedium)
                            Text("Categoría: ${product.category}", style = MaterialTheme.typography.bodySmall)
                            Text("Precio: ${product.price} CLP", style = MaterialTheme.typography.bodyMedium)
                            Spacer(Modifier.height(6.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Button(onClick = { carritoVM.addToCart(product) }) {
                                    Text("Agregar al carrito")
                                }
                                OutlinedButton(onClick = { carritoVM.removeFromCart(product.id) }) {
                                    Text("Quitar")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
