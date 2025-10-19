package com.example.one_teach.ui.screens

import android.widget.Space
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.one_teach.navigation.Route
import com.example.one_teach.ui.components.AppScaffold
import com.example.one_teach.ui.components.BottomBar
import com.example.one_teach.viewmodel.ConfigViewModel

@Composable
fun HomeScreen(nav: NavController, vm: ConfigViewModel){
    val modo by vm.modoVendedor.collectAsState()
    AppScaffold(nav, tittle = "OneTech", bottomBar = { BottomBar(nav)}) { modifier ->
        Column ( modifier.fillMaxSize().padding(16.dp)) {
            Text("Modo vendedor", style = MaterialTheme.typography.titleLarge)
            Row (verticalAlignment = Alignment.CenterVertically) {
                Text(if(modo) "Activo" else "inactivo")
                Switch(checked = modo, onCheckedChange = {vm.toggleModo() })
            }
            AnimatedVisibility(visible = modo) {
                Text("Modo vendedor activo", color = MaterialTheme.colorScheme.primary)
            }
            Spacer(Modifier.height(24.dp))
            Button(onClick = {nav.navigate(Route.Register.path)}) {
                Text("Registrar producto")
            }
            Spacer(Modifier.height(8.dp))
            Button(onClick = {nav.navigate(Route.Perfil.path)}) {
                Text("Ir a perfil")
            }
        }
    }
}