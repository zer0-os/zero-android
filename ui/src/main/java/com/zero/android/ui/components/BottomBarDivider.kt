package com.zero.android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zero.android.ui.extensions.Preview
import com.zero.android.ui.theme.AppTheme

@Composable
fun BottomBarDivider(modifier: Modifier = Modifier) {
	Spacer(
		modifier =
		modifier
			.fillMaxWidth()
			.height(1.dp)
			.background(
				brush =
				Brush.horizontalGradient(
					colors =
					listOf(
						AppTheme.colors.surfaceInverse,
						AppTheme.colors.glow,
						AppTheme.colors.surfaceInverse
					)
				)
			)
	)
}

@Preview @Composable
fun BottomBarDividerPreview() = Preview { BottomBarDivider() }
