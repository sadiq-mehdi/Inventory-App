package com.example.inventorymanagement.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = ClaudeOrange,
    onPrimary = ClaudeWhite,

    secondary = ClaudeGreen,
    onSecondary = ClaudeWhite,

    background = ClaudeCream,
    onBackground = ClaudeDarkBrown,

    surface = ClaudeWhite,
    onSurface = ClaudeDarkBrown,

    error = ClaudeRed,
    onError = ClaudeWhite
)

private val DarkColorScheme = darkColorScheme(
    primary = ClaudeOrange,
    onPrimary = ClaudeDarkBrown,

    secondary = ClaudeGreen,
    onSecondary = ClaudeDarkBrown,

    background = ClaudeDarkBrown,
    onBackground = ClaudeCream,

    surface = ClaudeBrown,
    onSurface = ClaudeCream,

    error = ClaudeRed,
    onError = ClaudeWhite
)

@Composable
fun InventoryManagementTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // your Typography.kt
        content = content
    )
}

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */