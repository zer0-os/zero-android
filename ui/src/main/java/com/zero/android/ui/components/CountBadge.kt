package com.zero.android.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zero.android.ui.extensions.Preview
import com.zero.android.ui.theme.AppTheme
import com.zero.android.ui.theme.Typography

@Composable
fun CountBadge(
	modifier: Modifier = Modifier,
	style: TextStyle = Typography.labelLarge,
	count: Int
) {
	Badge(
		modifier = modifier.padding(horizontal = 4.dp),
		containerColor = MaterialTheme.colorScheme.primary,
		contentColor = AppTheme.colors.colorTextPrimary
	) {
		Text(text = count.toString(), style = style, color = Color.White)
	}
}

@Preview @Composable
fun CountBadgePreview() = Preview { CountBadge(count = 1) }
