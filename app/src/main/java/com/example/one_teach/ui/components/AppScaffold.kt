package com.example.one_teach.ui.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    nav: NavController,                        // lo dejo igual: nav
    tittle: String,                            // lo dejo igual: tittle
    bottomBar: @Composable () -> Unit = {},    // 🔧 no-null para evitar ?.invoke()
    content: @Composable (Modifier) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = tittle) },
                colors = TopAppBarDefaults.topAppBarColors(
                    // 🎨 tu azul (igual que el de la BottomBar)
                    containerColor = Color(0xFF283593),
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = bottomBar,                  // ✅ ya no es nullable
        contentWindowInsets = WindowInsets.safeDrawing
    ) { innerPadding ->
        content(Modifier.padding(innerPadding))
    }
}
