package com.example.proyecto.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = PinkLight_themeDark,
    secondary = Grey_themeDark,
    tertiary = BrownLight_themeDark,
    background = Color.Black,
    surface = LightPink_themeDark,
    onPrimary = GrayLight_themeDark,
    onSecondary = Black_themeDark,
    onTertiary = RedLight_themeDark,
    onBackground = BeigeDark_themeDark,
    onSurface = Gray_themeDark
)

private val LightColorScheme = lightColorScheme(
    primary = PinkDark_themeLight,
    secondary = BrownLight_themeLight,
    tertiary = PinkGrey_themeLight,
    background = Color.White,
    surface = PinkLight_themeLight,
    onPrimary = White_themeLight,
    onSecondary = GrayWhite_themeLight,
    onTertiary = RedLight_themeLight,
    onBackground = Beige_themeLight,
    onSurface = GrayWhite_themeLight
)

@Composable
fun ProyectoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
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