package com.zero.android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zero.android.common.extensions.initials
import com.zero.android.ui.theme.AppTheme

@Composable
fun NameInitialsView(modifier: Modifier = Modifier, userName: String) {
	Box(
		modifier =
		modifier
			.size(36.dp)
			.background(color = MaterialTheme.colorScheme.primary, shape = CircleShape)
	) {
		Text(
			userName.initials,
			modifier = Modifier.align(Alignment.Center),
			color = AppTheme.colors.colorTextPrimary,
			fontWeight = FontWeight.Medium,
			fontSize = 16.sp
		)
	}
}
