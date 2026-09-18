package com.chris.studyplanner.ui.theme

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = CoralAccent,
    onPrimary = TextOnAccent,
    secondary = CoralAccentDim,
    onSecondary = TextOnAccent,

    background = DarkBackground,
    onBackground = TextPrimary,

    surface = DarkSurface,
    onSurface = TextPrimary,

    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = TextSecondary,

    outline = OutlineSubtle,
    outlineVariant = OutlineCoral
)

@Composable
fun StudyPlannerTheme(
    darkTheme: Boolean = true, // force dark to match the coral design; use isSystemInDarkTheme() to respect system setting
    dynamicColor: Boolean = false, // turn off dynamic color so your coral palette isn't overridden by wallpaper-based color on Android 12+
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            dynamicDarkColorScheme(context)
        }
        else -> DarkColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}