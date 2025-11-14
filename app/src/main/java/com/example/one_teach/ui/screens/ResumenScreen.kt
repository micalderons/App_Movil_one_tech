package com.example.one_teach.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.one_teach.ui.components.AppScaffold
import com.example.one_teach.ui.components.BottomBar
import com.example.one_teach.ui.utilss.WindowWidth
import com.example.one_teach.ui.utilss.rememberWindowWidthClass
import com.example.one_teach.viewmodel.ProductoViewModel
import com.example.one_teach.model.CartItem
import java.text.NumberFormat
import java.util.*

@Composable
fun ResumenScreen(nav: NavController, vm: ProductoViewModel) {
    val widthClass = rememberWindowWidthClass()
    val cart by vm.cart.collectAsState()
    val total = cart.sumOf { it.price * it.qty }
    val currentRoute = nav.currentBackStackEntryAsState().value?.destination?.route



    val money = remember {
        NumberFormat.getCurrencyInstance(Locale("es", "CL")).apply {
            currency = Currency.getInstance("CLP")
            maximumFractionDigits = 0
        }
    }

    AppScaffold(
        nav = nav,
        tittle = "Resumen",
        bottomBar = { BottomBar(navController = nav, currentRoute = currentRoute) }
    ) { modifier ->

        if (widthClass == WindowWidth.Compact) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(top = 8.dp)
            ) {
                if (cart.isEmpty()) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Tu carrito está vacío", style = MaterialTheme.typography.titleMedium)
                    }
                } else {
                    CartContent(cart = cart, money = money, total = total, vm = vm)
                }
            }
        } else {

            Row(
                modifier = modifier
                    .fillMaxSize()
                    .padding(top = 8.dp)
            ) {
                Box(Modifier.weight(1f)) { /* lateral opcional */ }
                Divider(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp),
                    color = MaterialTheme.colorScheme.outlineVariant
                )
                Column(Modifier.weight(2f)) {
                    if (cart.isEmpty()) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Tu carrito está vacío", style = MaterialTheme.typography.titleMedium)
                        }
                    } else {
                        CartContent(cart = cart, money = money, total = total, vm = vm)
                    }
                }
            }
        }
    }
}

@Composable
private fun CartContent(
    cart: List<CartItem>,
    money: NumberFormat,
    total: Int,
    vm: ProductoViewModel
) {
    Column(Modifier.fillMaxSize()) {

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HeaderCell("Producto", 0.40f)
            HeaderCell("Cant.", 0.15f, TextAlign.Center)
            HeaderCell("Precio", 0.20f, TextAlign.End)
            HeaderCell("Subtotal", 0.25f, TextAlign.End)
        }
        Divider()

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 4.dp)
        ) {
            items(cart, key = { it.id }) { item ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BodyCell(item.name, 0.40f)
                    BodyCell("${item.qty}", 0.15f, TextAlign.Center)
                    BodyCell(money.format(item.price), 0.20f, TextAlign.End)
                    BodyCell(money.format(item.price * item.qty), 0.25f, TextAlign.End) // subtotal calculado
                }
                Divider()
            }
        }


        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text("Total: ", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.width(8.dp))
            Text(
                money.format(total),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }


        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = { vm.clearCart() },
                modifier = Modifier.weight(1f)
            ) {
                Text("Vaciar carrito")
            }
            Button(
                onClick = {
                    // nav.navigate(Route.Confirmacion.path)
                },
                modifier = Modifier.weight(1f)
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
