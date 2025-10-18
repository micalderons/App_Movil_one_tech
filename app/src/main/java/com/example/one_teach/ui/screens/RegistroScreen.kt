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
import com.example.one_teach.viewmodel.ProductoViewModel

@Composable
fun RegistroScreen(nav: NavController, vm: ProductoViewModel){
    val ui by vm.ui.collectAsState()
    val valido = ui.errores.isEmpty() &&
            ui.nombre.isNotBlank() && ui.precio.toDoubleOrNull()?.let { it > 0} == true &&
            ui.descripcion.length >= 10 && ui.stock.toIntOrNull()?.let { it >= 0} == true && ui.acepta
}