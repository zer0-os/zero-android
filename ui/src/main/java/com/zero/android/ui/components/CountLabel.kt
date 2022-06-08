package com.zero.android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zero.android.ui.theme.AppTheme
import com.zero.android.ui.theme.Typography

@Composable
fun CountLabel(modifier: Modifier = Modifier, text: String) {
	Text(
		text = text,
		modifier =
		modifier
			.wrapContentSize()
			.background(MaterialTheme.colorScheme.primary, RoundedCornerShape(30.dp))
			.padding(horizontal = 6.dp, vertical = 2.dp),
		color = AppTheme.colors.colorTextSecondary,
		style = Typography.labelLarge
	)
}
