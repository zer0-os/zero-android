package com.zero.android.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val DarkColorScheme =
	darkColorScheme(
		primary = Purple200,
		secondary = Purple700,
		tertiary = Teal200,
		background = Black,
		error = Red300
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
		colorTextSecondaryVariant = White,
		success = EmeraldGreen,
		glow = BlueViolet,
		divider = Gray
	)

@Composable
fun ZeroTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
	val colorsScheme = DarkColorScheme
	val extendedColors = DarkExtendedColorPalette

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
