package com.example.one_teach.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.one_teach.ui.components.AppScaffold
import com.example.one_teach.viewmodel.ConfigViewModel

@Composable
fun HomeScreen(nav: NavController, vm: ConfigViewModel){
    val modo by vm.modoVendedor.collectAsState()
    AppScaffold(nav, tittle = "OneTech", bottomBar = { BottomBar(nav)}) { }
}