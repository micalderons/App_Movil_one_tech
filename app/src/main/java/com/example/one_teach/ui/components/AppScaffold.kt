@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.one_teach.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.navigation.NavController

@Composable
fun AppScaffold(
    nav: NavController,
    tittle: String,
    bottomBar: @Composable (() -> Unit)? = null,
    snackbarHostState: SnackbarHostState? = null,
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable (Modifier) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(tittle) },
                actions = actions
            )
        },
        bottomBar = { bottomBar?.invoke() },
        snackbarHost = { snackbarHostState?.let { SnackbarHost(it) } }
    ) { padding -> content(Modifier.padding(padding)) }
}

