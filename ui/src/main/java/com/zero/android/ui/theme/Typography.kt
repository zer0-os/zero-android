package com.zero.android.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography =
	Typography(
		bodyMedium =
		TextStyle(
			fontFamily = FontFamily.Default,
			fontWeight = FontWeight.Normal,
			fontSize = 14.sp
		),
		bodyLarge =
		TextStyle(
			fontFamily = FontFamily.Default,
			fontWeight = FontWeight.Normal,
			fontSize = 16.sp
		),
		labelLarge =
		TextStyle(
			fontFamily = FontFamily.Default,
			fontWeight = FontWeight.Medium,
			fontSize = 12.sp
		)
	)
