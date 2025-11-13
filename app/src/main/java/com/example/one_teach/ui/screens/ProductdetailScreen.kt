@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.one_teach.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.one_teach.navigation.Route
import com.example.one_teach.ui.components.BottomBar
import com.example.one_teach.viewmodel.HomeViewModel
import com.example.one_teach.viewmodel.ProductoViewModel

@Composable
fun ProductDetailScreen(
    nav: NavController,
    productId: String,
    homeVM: HomeViewModel,
    carritoVM: ProductoViewModel
) {
    val cs = MaterialTheme.colorScheme


    val products by homeVM.products.collectAsState(initial = emptyList())
    val product = products.firstOrNull { it.id == productId }

    var qty by remember { mutableIntStateOf(1) }

    Scaffold(
        bottomBar = {
            BottomBar(navController = nav, currentRoute = Route.Home.path)
        }
    ) { innerPadding ->

        if (product == null) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(cs.background),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            //  Barra superior
            TopAppBar(
                title = { Text(product.name) },
                navigationIcon = {
                    IconButton(onClick = { nav.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )

            // 🖼 Imagen
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                tonalElevation = 2.dp
            ) {
                Image(
                    painter = painterResource(id = product.image),
                    contentDescription = product.name,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Nombre + precio
            Column(Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${product.price} CLP",
                    style = MaterialTheme.typography.titleMedium,
                    color = cs.primary
                )
            }

            // Cantidad + botón Añadir
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row(verticalAlignment = Alignment.CenterVertically) {
                    FilledIconButton(
                        onClick = { if (qty > 1) qty-- },
                        shape = CircleShape
                    ) {
                        Icon(Icons.Default.Remove, contentDescription = "Restar")
                    }

                    Text(
                        qty.toString(),
                        modifier = Modifier.padding(horizontal = 16.dp),
                        style = MaterialTheme.typography.titleMedium
                    )

                    FilledIconButton(
                        onClick = { qty++ },
                        shape = CircleShape
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Sumar")
                    }
                }

                Button(
                    onClick = {

                        carritoVM.addToCart(product, qty)
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(cs.primary, cs.onPrimary)
                ) {
                    Text("Añadir")
                }
            }

            // Descripción
            Column(Modifier.padding(horizontal = 16.dp)) {
                Text(
                    "Descripción",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    product.description,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            // Reseñas
            Column(Modifier.padding(16.dp)) {
                Text(
                    "Reseñas",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                OutlinedTextField(
                    value = "",
                    onValueChange = { /* TODO: en el futuro */ },
                    label = { Text("Escribe tu reseña...") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    "Aún nadie ha realizado una reseña de este producto. ¡Puedes ser el primero!",
                    color = cs.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
