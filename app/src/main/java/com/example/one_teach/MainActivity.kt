package com.example.one_teach

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.one_teach.data.network.NetworkProductRepository
import com.example.one_teach.navigation.AppNavHost
import com.example.one_teach.ui.theme.One_TeachAppTheme
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Minimal smoke test: verify backend connectivity on app start (logs only)
        lifecycleScope.launch {
            val tag = "NetworkSmoke"
            val repo = NetworkProductRepository()
            val result = repo.fetchProducts()
            result.onSuccess { list ->
                Log.d(tag, "Fetched products: count=${list.size} first=${list.firstOrNull()} ")
            }.onFailure { e ->
                Log.e(tag, "Failed to fetch products", e)
            }
        }

        setContent {
            One_TeachAppTheme {
                val navController = rememberNavController()
                AppNavHost(navController)
            }
        }
    }
}
