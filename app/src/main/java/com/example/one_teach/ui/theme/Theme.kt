package com.example.one_teach.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = primaryForegroundDark,
    primaryContainer = popoverDark,
    onPrimaryContainer = popoverForegroundDark,
    secondary = secondaryDark,
    onSecondary = secondaryForegroundDark,
    secondaryContainer = mutedDark,
    onSecondaryContainer = mutedForegroundDark,
    tertiary = accentDark,
    onTertiary = accentForegroundDark,
    tertiaryContainer = inputDark,
    onTertiaryContainer = foregroundDark,
    error = destructiveDark,
    onError = destructiveForegroundDark,
    background = backgroundDark,
    onBackground = foregroundDark,
    surface = cardDark,
    onSurface = cardForegroundDark,
    surfaceVariant = popoverDark,
    onSurfaceVariant = popoverForegroundDark,
    outline = borderDark
)

@Composable
fun One_TeachAppTheme(
    darkTheme: Boolean = true, // Forzar tema oscuro
    dynamicColor: Boolean = false, // Desactivar color dinámico
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val colorScheme = DarkColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        val window = (context as? Activity)?.window
        if (window != null) {
            window.statusBarColor = colorScheme.surface.toArgb()
            window.navigationBarColor = colorScheme.surface.toArgb()
            val insetsDarkIcons = !darkTheme
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = insetsDarkIcons
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = insetsDarkIcons
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
