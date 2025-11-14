import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.text.NumberFormat
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember

@Composable
fun CartSummaryWithDiscount(
    subtotal: Int,
    money: NumberFormat,
    onTotalChanged: (Int) -> Unit
) {
    var code by remember { mutableStateOf("") }
    var appliedCode by remember { mutableStateOf<String?>(null) }


    val result = remember(subtotal, appliedCode) {
        applyDiscountCode(subtotal, appliedCode)
    }
    val discount = result.discountAmount
    val finalTotal = result.finalTotal


    LaunchedEffect(subtotal, discount) {
        onTotalChanged(finalTotal)
    }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        OutlinedTextField(
            value = code,
            onValueChange = { code = it },
            label = { Text("Código de descuento") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                appliedCode = code
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Aplicar código")
        }

        Divider()

        Text("Subtotal: ${money.format(subtotal)}")

        if (discount > 0) {
            Text(
                "Descuento 10%: -${money.format(discount)}",
                color = MaterialTheme.colorScheme.primary
            )
        } else {
            Text(
                "Descuento 10%: \$0",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Text(
            "Total a pagar: ${money.format(finalTotal)}",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}



