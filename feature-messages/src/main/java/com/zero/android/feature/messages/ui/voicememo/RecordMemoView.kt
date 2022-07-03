package com.zero.android.feature.messages.ui.voicememo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.zero.android.common.R
import com.zero.android.ui.components.DotsBounceAnimation
import com.zero.android.ui.components.Timer
import com.zero.android.ui.theme.AppTheme

@Composable
fun RecordMemoView(
    onCancel: () -> Unit,
    onSendMemo: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 20.dp)
    ) {
        Timer(Modifier.align(Alignment.CenterVertically))
        Spacer(modifier = Modifier.size(6.dp))
        DotsBounceAnimation(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.size(6.dp))
        IconButton(
            onClick = onCancel,
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.CenterVertically)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_cancel_24),
                contentScale = ContentScale.Fit,
                contentDescription = "cd_cancel"
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        IconButton(
            onClick = onSendMemo,
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.CenterVertically)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_send_24),
                contentScale = ContentScale.Fit,
                contentDescription = "cd_send",
                colorFilter = ColorFilter.tint(AppTheme.colors.glow),
            )
        }
    }
}
