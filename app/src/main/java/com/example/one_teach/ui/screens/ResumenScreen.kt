package com.example.one_teach.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.one_teach.ui.components.AppScaffold
import com.example.one_teach.ui.components.BottomBar
import com.example.one_teach.ui.utils.WindowWidth
import com.example.one_teach.ui.utils.rememberWindowWidthClass

import com.example.one_teach.viewmodel.ProductoViewModel


@Composable
fun ResumenScreen(nav: NavController, vm: ProductoViewModel){
    val ui by vm.ui.collectAsState()
    val widthClass = rememberWindowWidthClass()

    AppScaffold(nav, tittle = "Resumen", bottomBar = { BottomBar(nav) }){ modifier ->
        if (widthClass == WindowWidth.Compact){
            Column (modifier.fillMaxSize().padding(16.dp)){
                Text("Producto Registrado", style = MaterialTheme.typography.titleLarge)
                Text("Nombre: ${ui.nombre}")
                Text("Precio: ${ui.precio}")
                Text("Precio: ${ui.precio}")
                Text("Descripcion: ${ui.descripcion}")
                Text("Stock: ${ui.stock}")
            }
        } else {
            Row(modifier.fillMaxSize().padding(16.dp)){
                Column (Modifier.weight(1f)){
                    Text("Favoritos (mock)", style = MaterialTheme.typography.titleLarge)
                    Text("")
                }
                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant
                )
                Column(modifier.weight(1f).padding(start = 16.dp)) {
                    Text("Resumen", style = MaterialTheme.typography.titleLarge)
                    Text("Nombre: ${ui.nombre}")
                    Text("Precio: ${ui.precio}")
                    Text("Descripcion: ${ui.descripcion}")
                    Text("Stock: ${ui.stock}")
                }
            }
        }
    }
}