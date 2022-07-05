package com.zero.android.feature.messages.ui.voicememo

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zero.android.common.R
import com.zero.android.common.extensions.convertDurationToString
import com.zero.android.feature.messages.ui.voicememo.mediaPlayer.MediaSourceViewModel
import com.zero.android.models.Message
import com.zero.android.ui.components.ReverseTimer
import com.zero.android.ui.theme.AppTheme

enum class VoiceMessageState {
    DOWNLOAD, DOWNLOADING, PLAYING, STOPPED
}

@Composable
fun VoiceMessage(message: Message, viewModel: MediaSourceViewModel) {
    val mediaSourceProvider = viewModel.getMediaSource(message)
    val mediaFileState = mediaSourceProvider.currentFileState.collectAsState()
    val sliderPosition = mediaSourceProvider.currentPosition.collectAsState()
    val mediaDuration = mediaSourceProvider.mediaFileDuration.collectAsState()
    val memoState = mediaFileState.value

    DisposableEffect(Unit) {
        onDispose { viewModel.dispose() }
    }

    val iconRes = when (memoState) {
        VoiceMessageState.DOWNLOAD -> R.drawable.ic_download_circle_24
        VoiceMessageState.PLAYING -> R.drawable.ic_stop_circle_24
        else -> R.drawable.ic_play_circle_24
    }
    Row(modifier = Modifier.wrapContentWidth()) {
        if (memoState == VoiceMessageState.DOWNLOADING) {
            CircularProgressIndicator(
                color = AppTheme.colors.glow,
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.CenterVertically)
            )
        } else {
            IconButton(
                onClick = {
                    when (memoState) {
                        VoiceMessageState.DOWNLOAD -> viewModel.downloadAndPrepareMedia(message)
                        VoiceMessageState.STOPPED -> viewModel.play(message)
                        VoiceMessageState.PLAYING -> viewModel.stop()
                        else -> {}
                    }
                }, modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(iconRes),
                    contentDescription = null
                )
            }
        }
        Spacer(modifier = Modifier.size(4.dp))
        Slider(
            modifier = Modifier
                .width(150.dp)
                .align(Alignment.CenterVertically),
            value = sliderPosition.value,
            onValueChange = { viewModel.seekMediaTo(message, it) },
            valueRange = 0f..mediaDuration.value.div(1000).toFloat(),
            colors = SliderDefaults.colors(
                thumbColor = AppTheme.colors.glow,
                activeTrackColor = AppTheme.colors.colorTextPrimary
            )
        )
        Spacer(modifier = Modifier.size(4.dp))
        if (memoState == VoiceMessageState.PLAYING) {
            ReverseTimer(startTime = mediaDuration.value, modifier = Modifier.align(Alignment.CenterVertically))
        } else {
            Text(
                text = if (mediaDuration.value > 0) {
                    mediaDuration.value.convertDurationToString()
                } else "-", modifier = Modifier.align(Alignment.CenterVertically),
                fontSize = 14.sp
            )
        }
    }
}
