package com.example.one_teach.ui.screens.policies

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.one_teach.navigation.Route
import com.example.one_teach.ui.components.AppScaffold
import com.example.one_teach.ui.components.BottomBar
import com.example.one_teach.ui.components.MoreMenu

@Composable
fun PoliciesScreen(nav: NavController) {
    val currentRoute = nav.currentBackStackEntryAsState().value?.destination?.route

    AppScaffold(
        nav = nav,
        tittle = "Políticas",
        bottomBar = { BottomBar(navController = nav, currentRoute = currentRoute) },
        actions = { MoreMenu(nav) }
    ) { modifier ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Políticas y derechos reservados", style = MaterialTheme.typography.headlineSmall)

            Text(
                "• Uso del servicio: El contenido y las funcionalidades de esta aplicación se entregan tal cual. " +
                        "Nos reservamos el derecho de realizar mejoras y cambios sin previo aviso.\n\n" +
                        "• Privacidad: Solo se utilizan los datos necesarios para el funcionamiento de la app. " +
                        "No se comparten datos personales con terceros sin consentimiento.\n\n" +
                        "• Propiedad intelectual: Marcas, logotipos y contenido visual/textual pertenecen a sus respectivos dueños. " +
                        "Queda prohibida su reproducción no autorizada.\n\n" +
                        "• Responsabilidad: No nos hacemos responsables por daños derivados del uso indebido de la aplicación.\n\n" +
                        "© ${java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)} ONE-TECH. Todos los derechos reservados.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start
            )
        }
    }
}
