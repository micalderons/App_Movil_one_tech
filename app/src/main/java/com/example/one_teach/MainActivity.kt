package com.example.one_teach

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.one_teach.navigation.AppNavHost
import com.example.one_teach.repository.NetworkProductRepository
import com.example.one_teach.ui.theme.One_TeachAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Test de conexión mínima con backend (solo logs)
        lifecycleScope.launch {
            val tag = "NetworkSmoke"
            val repo = NetworkProductRepository()
            try {
                val products = repo.fetchProducts()
                Log.d(tag, "Fetched products: count=${products.size}, first=${products.firstOrNull()}")
            } catch (e: Exception) {
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
