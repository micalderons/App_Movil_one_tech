package com.example.one_teach.ui.screens.reviews

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.one_teach.navigation.Route
import com.example.one_teach.ui.components.AppScaffold
import com.example.one_teach.ui.components.BottomBar
import com.example.one_teach.ui.components.MoreMenu
import com.example.one_teach.viewmodel.HomeViewModel
import kotlin.random.Random

data class Review(
    val author: String,
    val rating: Int,     // 1..5
    val comment: String
)

@Composable
fun ReviewsScreen(
    nav: NavController,
    homeVM: HomeViewModel = viewModel()
) {
    val products by homeVM.products.collectAsState(initial = emptyList())
    val currentRoute = nav.currentBackStackEntryAsState().value?.destination?.route

    // Selecciona 4 productos al azar (si hay menos, toma los que haya)
    val selected = remember(products) { products.shuffled().take(4) }

    // Pool de comentarios “humanos” sencillos
    val commentsPool = listOf(
        "Tal como en las fotos, llegó bien y rápido.",
        "Llevo una semana usándolo y va perfecto.",
        "Buena relación precio/calidad. Recomendado.",
        "Tenía dudas pero cumplió con lo que necesitaba.",
        "El empaque venía bien, sin detalles.",
        "Funciona tal cual la descripción.",
        "Lo volvería a comprar sin problema.",
        "Me salvó para el trabajo, cómodo de usar."
    )
    val namesPool = listOf("Daniela", "Jorge", "Camila", "Felipe", "Constanza", "Luis", "Marcela", "Andrés")

    AppScaffold(
        nav = nav,
        tittle = "Reseñas",
        bottomBar = { BottomBar(navController = nav, currentRoute = currentRoute) },
        actions = { MoreMenu(nav) }
    ) { modifier ->
        if (selected.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text("Aún no hay productos para reseñar")
            }
            return@AppScaffold
        }

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(selected, key = { it.id }) { product ->
                // Genera 2-3 reseñas por producto
                val reviews = remember(product.id) {
                    List(Random.nextInt(2, 4)) {
                        Review(
                            author = namesPool.random(),
                            rating = Random.nextInt(4, 6).coerceAtMost(5),
                            comment = commentsPool.random()
                        )
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Text(product.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                        Spacer(Modifier.height(6.dp))

                        reviews.forEach { r ->
                            ReviewRow(r)
                            Divider(Modifier.padding(vertical = 6.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ReviewRow(r: Review) {
    Column {
        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            Text(r.author, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
            Spacer(Modifier.width(8.dp))
            repeat(r.rating) {
                Icon(Icons.Filled.Star, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            }
        }
        Spacer(Modifier.height(4.dp))
        Text(r.comment, style = MaterialTheme.typography.bodyMedium)
    }
}
