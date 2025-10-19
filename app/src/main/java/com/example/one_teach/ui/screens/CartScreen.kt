package com.example.one_teach.ui.screens.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.one_teach.navigation.Route
import com.example.one_teach.ui.components.AppScaffold
import com.example.one_teach.ui.components.BottomBar
import com.example.one_teach.viewmodel.ProductoViewModel
import java.text.NumberFormat
import java.util.*

@Composable
fun CartScreen(
    nav: NavController,
    vm: ProductoViewModel = viewModel()
) {
    val cart by vm.cart.collectAsState()
    val currentRoute = nav.currentBackStackEntryAsState().value?.destination?.route

    // CLP formatter
    val money = remember {
        NumberFormat.getCurrencyInstance(Locale("es", "CL")).apply {
            currency = Currency.getInstance("CLP")
            maximumFractionDigits = 0
        }
    }

    val total = cart.sumOf { it.price * it.qty }

    AppScaffold(
        nav = nav,
        tittle = "Carrito",
        bottomBar = { BottomBar(navController = nav, currentRoute = currentRoute) }
    ) { modifier ->

        if (cart.isEmpty()) {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Tu carrito está vacío", style = MaterialTheme.typography.titleMedium)
            }
            return@AppScaffold
        }

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Encabezado
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HeaderCell("Producto", 0.40f)
                HeaderCell("Cant.", 0.20f, TextAlign.Center)
                HeaderCell("Precio", 0.20f, TextAlign.End)
                HeaderCell("Subtotal", 0.20f, TextAlign.End)
            }
            Divider()

            // Lista
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 6.dp)
            ) {
                items(cart, key = { it.id }) { item ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BodyCell(item.name, 0.40f)

                        // Cantidad con stepper
                        Row(
                            modifier = Modifier
                                .weight(0.20f)
                                .padding(horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            IconButton(
                                onClick = { vm.updateQty(item.id, item.qty - 1) },
                                enabled = item.qty > 1
                            ) {
                                Icon(Icons.Filled.Remove, contentDescription = "Menos")
                            }
                            Text(
                                text = "${item.qty}",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.width(28.dp),
                                textAlign = TextAlign.Center
                            )
                            IconButton(onClick = { vm.updateQty(item.id, item.qty + 1) }) {
                                Icon(Icons.Filled.Add, contentDescription = "Más")
                            }
                        }

                        BodyCell(money.format(item.price), 0.20f, TextAlign.End)
                        BodyCell(money.format(item.price * item.qty), 0.20f, TextAlign.End)

                        IconButton(onClick = { vm.removeFromCart(item.id) }) {
                            Icon(Icons.Filled.Delete, contentDescription = "Eliminar")
                        }
                    }
                    Divider()
                }
            }

            // Total y acciones
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(onClick = { vm.clearCart() }) {
                    Text("Vaciar carrito")
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Total: ", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        money.format(total),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = {
                    // TODO: continuar a flujo de pago / dirección
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Continuar compra")
            }
        }
    }
}

@Composable
private fun RowScope.HeaderCell(
    text: String,
    weight: Float,
    align: TextAlign = TextAlign.Start
) {
    Text(
        text = text,
        modifier = Modifier.weight(weight),
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.Bold,
        textAlign = align
    )
}

@Composable
private fun RowScope.BodyCell(
    text: String,
    weight: Float,
    align: TextAlign = TextAlign.Start
) {
    Text(
        text = text,
        modifier = Modifier.weight(weight),
        style = MaterialTheme.typography.bodyMedium,
        textAlign = align
    )
}
