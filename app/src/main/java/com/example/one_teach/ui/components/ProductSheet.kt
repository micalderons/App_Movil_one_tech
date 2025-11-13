package com.example.one_teach.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ProductSheet(
    nav: NavController,
    title: String,
    category: String,
    priceLabel: String,
    inCart: Boolean,
    onAddToCart: () -> Unit,
    onRemoveFromCart: () -> Unit,
    onDismiss: () -> Unit,
    onViewProduct: () -> Unit
) {
    val cs = MaterialTheme.colorScheme
    val pill = RoundedCornerShape(24.dp)

    Column(
        Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(title, style = MaterialTheme.typography.titleLarge, color = cs.onBackground)
        Text("Categoría: $category", color = cs.onSurfaceVariant)
        Text("Precio: $priceLabel", color = cs.onBackground)

        // Ver producto
        OutlinedButton(
            onClick = {
                onDismiss()
                onViewProduct()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = pill,
            colors = ButtonDefaults.outlinedButtonColors(contentColor = cs.onSurface)
        ) {
            Text("Ver producto")
        }

        // Agregar / Quitar del carrito
        Button(
            onClick = { onAddToCart() },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = pill,
            colors = ButtonDefaults.buttonColors(cs.primary, cs.onPrimary)
        ) {
            Text("Agregar al carrito")
        }

        if (inCart) {
            OutlinedButton(
                onClick = { onRemoveFromCart() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = pill
            ) {
                Text("Quitar del carrito")
            }
        }

        TextButton(
            onClick = onDismiss,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancelar")
        }
    }
}
