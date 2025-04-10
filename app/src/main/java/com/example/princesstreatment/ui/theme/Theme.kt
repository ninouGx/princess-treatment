package com.example.princesstreatment.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    background = OledBlack,
    onBackground = PrincessOnBackground,
    surface = PrincessSurface,
    onSurface = PrincessOnSurface,
    surfaceVariant = PrincessSurfaceVariant,
    primary = PrincessPrimary,
    onPrimary = PrincessOnPrimary,
    secondary = PrincessSecondary,
    onSecondary = PrincessOnSecondary,
    tertiary = PrincessTertiary,
    onTertiary = PrincessOnTertiary
)

private val LightColorScheme = lightColorScheme(
    background = PrincessLightBackground,
    onBackground = PrincessLightOnBackground,
    surface = PrincessLightSurface,
    onSurface = PrincessLightOnSurface,
    surfaceVariant = PrincessLightSurfaceVariant,
    primary = PrincessLightPrimary,
    onPrimary = PrincessLightOnPrimary,
    secondary = PrincessLightSecondary,
    onSecondary = PrincessLightOnSecondary,
    tertiary = PrincessLightTertiary,
    onTertiary = PrincessLightOnTertiary,
)

@Composable
fun PrincessTreatmentTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}