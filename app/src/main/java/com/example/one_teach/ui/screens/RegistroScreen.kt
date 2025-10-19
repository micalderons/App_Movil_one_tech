package com.example.one_teach.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.one_teach.navigation.Route
import com.example.one_teach.ui.components.AppScaffold
import com.example.one_teach.ui.components.BottomBar
import com.example.one_teach.viewmodel.ProductoViewModel
import kotlin.collections.containsKey
import kotlin.text.get

@Composable
fun RegistroScreen(nav: NavController, vm: ProductoViewModel){
    val ui by vm.ui.collectAsState()
    val valido = ui.errores.isEmpty() &&
            ui.nombre.isNotBlank() && ui.precio.toDoubleOrNull()?.let { it > 0} == true &&
            ui.descripcion.length >= 10 && ui.stock.toIntOrNull()?.let { it >= 0} == true && ui.acepta

    val btnColor by animateColorAsState(
        if (valido) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
    )
    AppScaffold(nav, tittle = "Registrar", bottomBar = { BottomBar(nav) }) {modifier ->
        Column( modifier
            .fillMaxSize()
            .padding(16.dp)
        ){
            OutlinedTextField(
                value = ui.nombre, onValueChange = vm::onNombre,
                label = {Text("Nombre")}, isError = ui.errores.containsKey("nombre"),
                supportingText = {Text(ui.errores["nombre"] ?: "")}, singleLine = true
            )
            OutlinedTextField(
                value = ui.precio, onValueChange = vm::onPrecio,
                label = {Text("Precio")}, isError = ui.errores.containsKey("precio"),
                supportingText = {Text(ui.errores["precio"] ?: "")}, singleLine = true
            )
            OutlinedTextField(
                value = ui.descripcion, onValueChange = vm::onDescripcion,
                label = { Text("Descripción") }, isError = ui.errores.containsKey("descripcion"),
                supportingText = { Text(ui.errores["descripcion"] ?: "") }
            )
            OutlinedTextField(
                value = ui.stock, onValueChange = vm::onStock,
                label = { Text("Stock") }, isError = ui.errores.containsKey("stock"),
                supportingText = { Text(ui.errores["stock"] ?: "") }, singleLine = true
            )
            Row(verticalAlignment = Alignment.CenterVertically){
                Checkbox(checked = ui.acepta, onCheckedChange = vm::onAcepta)
                Text(
                    "Acepto los términos y condiciones",
                    color = if (ui.errores.containsKey("acepta")) MaterialTheme.colorScheme.error else LocalContentColor.current
                )

            }
            Button(
                onClick = {if (vm.valiar()) nav.navigate(Route.Resumen.path) },
                colors = ButtonDefaults.buttonColors(containerColor = btnColor)
            ){
                Text("Guardar y ver resumen")
            }

        }
    }
}