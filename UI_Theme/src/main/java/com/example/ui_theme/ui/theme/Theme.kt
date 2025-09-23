package com.example.ui_theme.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

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
    content: @Composable () -> Unit
) {
    // Se asegura que se haga uso de nuestros colores definidos para cada modo,
    // esto debido a que no era posible por el uso de DynamicColors de Material3
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}