package com.example.one_teach

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.one_teach.navigation.AppNavHost
import com.example.one_teach.ui.theme.One_TeachAppTheme


class MainActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            One_TeachAppTheme {
                val navController = rememberNavController()
                AppNavHost(navController)
            }
        }
    }
}
