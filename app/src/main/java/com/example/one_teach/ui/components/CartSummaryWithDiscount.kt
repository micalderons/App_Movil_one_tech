import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun CartSummaryWithDiscount(
    subtotal: Int
) {
    val cs = MaterialTheme.colorScheme

    var code by remember { mutableStateOf("") }
    var hasDiscount by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf<String?>(null) }

    // Calculamos descuento y total a pagar en base al subtotal actual
    val discount = remember(subtotal, hasDiscount) {
        if (hasDiscount) (subtotal * 0.10).roundToInt() else 0
    }
    val totalToPay = subtotal - discount

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        // Código de descuento
        OutlinedTextField(
            value = code,
            onValueChange = { code = it },
            label = { Text("Código de descuento") },
            placeholder = { Text("Ej: duocuc.cl") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                hasDiscount = code.trim().equals("duocuc.cl", ignoreCase = true)
                message = if (hasDiscount) {
                    "Código aplicado: 10% de descuento"
                } else {
                    "Código inválido"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Aplicar código")
        }

        message?.let {
            Text(
                text = it,
                color = if (hasDiscount) cs.primary else cs.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Divider(thickness = 1.dp)

        // Resumen de montos
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Total productos:")
                Text("$${subtotal}")
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Descuento:")
                Text(
                    text = if (discount > 0) "-$$discount" else "$0",
                    color = if (discount > 0) cs.primary else cs.onSurfaceVariant
                )
            }

            Divider(thickness = 1.dp)

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Total a pagar:",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    "$$totalToPay",
                    style = MaterialTheme.typography.titleMedium,
                    color = cs.primary
                )

            }
        }
    }
}

