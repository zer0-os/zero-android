package com.zero.android.feature.messages.ui.conversation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zero.android.common.R
import com.zero.android.ui.extensions.Preview
import com.zero.android.ui.theme.AppTheme
import com.zero.android.ui.theme.Typography

@Composable
fun ConversationBottomBar(
    modifier: Modifier = Modifier,
    onMessageSent: (String) -> Unit,
    resetScroll: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize()
            .background(AppTheme.colors.surfaceInverse)
            .alpha(0.7f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            IconButton(
                modifier = Modifier.weight(1f),
                onClick = {

                }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "cd_add_attachment"
                )
            }
            TextField(
                value = "",
                onValueChange = {

                },
                placeholder = { Text(stringResource(com.zero.android.feature.messages.R.string.write_your_message)) },
                textStyle = Typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(7f),
                shape = RoundedCornerShape(24.dp),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = AppTheme.colors.colorTextPrimary,
                    disabledTextColor = AppTheme.colors.colorTextSecondary,
                    focusedIndicatorColor = AppTheme.colors.colorTextSecondary,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    placeholderColor = AppTheme.colors.colorTextSecondary,
                    containerColor = Color.Gray
                )
            )
            IconButton(
                modifier = Modifier.weight(1f),
                onClick = {

                }) {
                Icon(
                    painter = painterResource(R.drawable.ic_camera),
                    contentDescription = "cd_add_attachment"
                )
            }
            IconButton(
                modifier = Modifier.weight(1f),
                onClick = {

                }) {
                Icon(
                    painter = painterResource(R.drawable.ic_mic),
                    contentDescription = "cd_record_audio"
                )
            }
        }
    }
}

@Preview
@Composable
fun ConversationBottomBarPreview() = Preview {
    ConversationBottomBar()
}
