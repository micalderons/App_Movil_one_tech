@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.one_teach.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.mutableStateListOf
import kotlinx.coroutines.launch




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
    var rating by remember { mutableIntStateOf(0) }
    var comment by remember { mutableStateOf("") }
    data class Review(val rating: Int, val comment: String)

    val reviews = remember { mutableStateListOf<Review>() }

// Para mostrar snackbars
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


    Scaffold(
        bottomBar = {
            BottomBar(navController = nav, currentRoute = Route.Home.path)
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
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
                .background(cs.background)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            //  Barra superior
            TopAppBar(
                title = { Text(product.name) },
                navigationIcon = {
                    IconButton(onClick = { nav.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
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

                Spacer(Modifier.height(8.dp))

                // Rating con estrellas
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    (1..5).forEach { star ->
                        IconButton(onClick = { rating = star }) {
                            val icon = if (rating >= star) {
                                Icons.Filled.Star
                            } else {
                                Icons.Outlined.StarBorder
                            }

                            Icon(
                                imageVector = icon,
                                contentDescription = "$star estrellas",
                                tint = if (rating >= star) cs.primary else cs.onSurfaceVariant
                            )
                        }
                    }

                    if (rating > 0) {
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = "$rating/5",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = comment,
                    onValueChange = { comment = it },
                    label = { Text("Escribe tu reseña...") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    "Aún nadie ha realizado una reseña de este producto. ¡Puedes ser el primero!",
                    color = cs.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.height(12.dp))

                Button(
                    onClick = {
                        if (rating == 0 || comment.isBlank()) {
                            // Aviso de error si falta algo
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    "Por favor, selecciona una valoración y escribe un comentario."
                                )
                            }
                        } else {
                            // Guardar reseña en memoria
                            reviews.add(Review(rating = rating, comment = comment))

                            // Limpiar campos
                            rating = 0
                            comment = ""

                            // Mostrar snackbar de éxito
                            scope.launch {
                                snackbarHostState.showSnackbar("¡Reseña enviada!")
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = cs.primary,
                        contentColor = cs.onPrimary
                    )
                ) {
                    Text("Enviar reseña")
                }
                Spacer(Modifier.height(16.dp))

                if (reviews.isEmpty()) {
                    Text(
                        "Aún nadie ha realizado una reseña de este producto. ¡Puedes ser el primero!",
                        color = cs.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    Text(
                        "Reseñas de otros usuarios",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(8.dp))

                    reviews.forEach { review ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            shape = RoundedCornerShape(8.dp),
                            color = cs.surfaceVariant
                        ) {
                            Column(Modifier.padding(8.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    (1..5).forEach { star ->
                                        Icon(
                                            imageVector = if (review.rating >= star)
                                                Icons.Filled.Star
                                            else
                                                Icons.Outlined.StarBorder,
                                            contentDescription = null,
                                            tint = cs.primary
                                        )
                                    }
                                }
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    review.comment,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}
