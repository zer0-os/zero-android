package com.zero.android.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val DarkColorScheme =
    darkColorScheme(
        primary = Purple200,
        secondary = Purple700,
        tertiary = Teal200,
        background = Black,
        error = Red300,
        outline = LavenderIndigo
    )

private val LightColorScheme =
    lightColorScheme(
        primary = Purple500,
        secondary = Purple700,
        tertiary = Teal200,
        background = White,
        error = Red300,
        outline = LavenderIndigo
    )

private val DarkExtendedColorPalette =
    ExtendedColor(
        chatBubblePrimary = PersianIndigo,
        chatBubblePrimaryVariant = RussianViolet,
        chatBubbleSecondary = CetaceanBlue,
        header = Platinum,
        headerVariant = RaisinBlack,
        buttonPrimary = RaisinBlack75,
        buttonSecondary = Gray,
        colorTextPrimary = White,
        colorTextSecondary = TaupeGray,
        colorTextSecondaryVariant = Gray,
        success = EmeraldGreen,
        glow = LavenderIndigo,
        divider = Gray,
        surface = White,
        surfaceVariant = Gray,
        surfaceInverse = Black
    )

private val LightExtendedColorPalette =
    ExtendedColor(
        chatBubblePrimary = CadetBlue,
        chatBubblePrimaryVariant = Rhythm,
        chatBubbleSecondary = CetaceanBlue,
        header = ChineseBlack,
        headerVariant = RaisinBlack,
        buttonPrimary = RaisinBlack75,
        buttonSecondary = Gray,
        colorTextPrimary = RaisinBlack75,
        colorTextSecondary = PhilippineSilver,
        colorTextSecondaryVariant = Gray,
        success = EmeraldGreen,
        glow = LavenderIndigo,
        divider = Gray,
        surface = Black,
        surfaceVariant = Gray,
        surfaceInverse = White
    )

@Composable
fun ZeroTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colorsScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val extendedColors = if (darkTheme) DarkExtendedColorPalette else LightExtendedColorPalette

    CompositionLocalProvider(LocalExtendedColors provides extendedColors) {
        MaterialTheme(
            colorScheme = colorsScheme,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}

object AppTheme {
    val colors: ExtendedColor
        @Composable get() = LocalExtendedColors.current
}
