import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.one_teach.model.DiscountResult
import com.example.one_teach.model.applyDiscountCode
import java.text.NumberFormat

@Composable
fun CartSummaryWithDiscount(
    subtotal: Int,
    money: NumberFormat,
    onTotalChanged: (Int) -> Unit
) {
    var code by remember { mutableStateOf("") }
    var appliedCode by remember { mutableStateOf<String?>(null) }


    val result = remember<DiscountResult>(key1 = subtotal, key2 = appliedCode) {
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

        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

        Text("Subtotal: ${money.format(subtotal)}")

        if (discount > 0) {
            Text(
                "Descuento 10%: -${money.format(discount)}",
                color = MaterialTheme.colorScheme.primary
            )
        } else {
            Text(
                "Descuento 10%: $0",
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



