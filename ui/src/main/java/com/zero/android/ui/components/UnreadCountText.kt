package com.zero.android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zero.android.ui.theme.AppTheme
import com.zero.android.ui.theme.Typography

@Composable
fun UnreadCountText(modifier: Modifier = Modifier, text: String) {
    Text(
        color = AppTheme.colors.surfaceInverse,
        text = text,
        modifier =
        modifier
            .background(color = AppTheme.colors.glow, shape = RoundedCornerShape(24.dp))
            .wrapContentHeight()
            .padding(6.dp, 2.dp, 6.dp, 2.dp),
        style = Typography.labelLarge
    )
}
