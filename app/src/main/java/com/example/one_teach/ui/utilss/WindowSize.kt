package com.example.one_teach.ui.utilss
import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.window.layout.WindowMetricsCalculator

enum class WindowWidth { Compact, Medium, Expanded}

@Composable
fun rememberWindowWidthClass(): WindowWidth{
    val activity = LocalContext.current as Activity
    val metrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(activity)
    val widthDp = metrics.bounds.width() / activity.resources.displayMetrics.density

    return when{
        widthDp < 600f -> WindowWidth.Compact
        widthDp < 840f -> WindowWidth.Medium
        else -> WindowWidth.Expanded
    }
}