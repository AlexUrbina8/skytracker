package com.example.skytracker.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF30C7F4),
    secondary = Color(0xFF7EE4FF),
    background = Color(0xFF07111F),
    surface = Color(0xFF0D2136),
    surfaceVariant = Color(0xFF132942),
    onPrimary = Color(0xFF07111F),
    onSecondary = Color(0xFF07111F),
    onBackground = Color.White,
    onSurface = Color.White,
    onSurfaceVariant = Color(0xFF6D89A8)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF0077A8),
    secondary = Color(0xFF0099CC),
    background = Color(0xFFF2F7FB),
    surface = Color.White,
    surfaceVariant = Color(0xFFE3EEF6),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color(0xFF07111F),
    onSurface = Color(0xFF07111F),
    onSurfaceVariant = Color(0xFF5B7288)
)

@Composable
fun SkyTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        val window = (view.context as Activity).window
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}