package com.zero.android.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Background(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
	Surface(
		color = MaterialTheme.colorScheme.background,
		tonalElevation = 0.dp,
		modifier = modifier.fillMaxSize()
	) { content() }
}
