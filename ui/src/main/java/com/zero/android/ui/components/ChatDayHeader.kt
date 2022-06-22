package com.zero.android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zero.android.ui.theme.AppTheme

@Composable
fun DayHeader(dayString: String) {
	Row(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp).height(16.dp)) {
		PreDayHeaderLine()
		Text(
			text = dayString,
			modifier = Modifier.padding(horizontal = 16.dp),
			style = MaterialTheme.typography.labelSmall,
			color = AppTheme.colors.colorTextSecondaryVariant
		)
		PostDayHeaderLine()
	}
}

@Composable
private fun RowScope.PreDayHeaderLine() {
	Box(
		modifier =
		Modifier.weight(1f)
			.height(0.5.dp)
			.align(Alignment.CenterVertically)
			.background(
				brush =
				Brush.horizontalGradient(
					colors =
					listOf(AppTheme.colors.surfaceInverse, AppTheme.colors.surface)
				)
			)
	)
}

@Composable
private fun RowScope.PostDayHeaderLine() {
	Box(
		modifier =
		Modifier.weight(1f)
			.height(0.5.dp)
			.align(Alignment.CenterVertically)
			.background(
				brush =
				Brush.horizontalGradient(
					colors =
					listOf(AppTheme.colors.surface, AppTheme.colors.surfaceInverse)
				)
			)
	)
}

@Preview @Composable
fun DayHeaderPrev() {}
