package com.zero.android.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val DarkColorPalette =
    darkColors(
        primary = Purple200,
        primaryVariant = Purple700,
        secondary = Teal200,
        background = Black,
        error = Red300
    )

private val LightColorPalette =
    lightColors(
        primary = Purple500,
        primaryVariant = Purple700,
        secondary = Teal200,
        background = Argent,
        error = Red300
    )

private val DarkExtendedColorPalette = ExtendedColor(
    chatBubblePrimary = PersianIndigo,
    chatBubblePrimaryVariant = RussianViolet,
    chatBubbleSecondary = CetaceanBlue,
    header = Platinum,
    headerVariant = RaisinBlack,
    buttonPrimary = RaisinBlack75,
    buttonSecondary = Gray,
    colorTextPrimary = RaisinBlack,
    colorTextSecondary = TaupeGray,
    colorTextSecondaryVariant = Black,
    success = EmeraldGreen,
    glow = BlueViolet,
)

private val LightExtendedColorPalette = ExtendedColor(
    chatBubblePrimary = CadetBlue,
    chatBubblePrimaryVariant = Rhythm,
    chatBubbleSecondary = CetaceanBlue,
    header = ChineseBlack,
    headerVariant = RaisinBlack,
    buttonPrimary = RaisinBlack75,
    buttonSecondary = Gray,
    colorTextPrimary = White,
    colorTextSecondary = PhilippineSilver,
    colorTextSecondaryVariant = OuterSpace,
    success = EmeraldGreen,
    glow = BlueViolet,
)

@Composable
fun ZeroTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette
    val extendedColors = if (darkTheme) DarkExtendedColorPalette else LightExtendedColorPalette
    CompositionLocalProvider(LocalExtendedColors provides extendedColors) {
        MaterialTheme(colors = colors, typography = Typography, shapes = Shapes, content = content)
    }
}

object ZeroExtendedTheme {
    val colors: ExtendedColor
        @Composable
        get() = LocalExtendedColors.current
}
