package com.zero.android.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.zero.android.common.extensions.convertDurationToString
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun Timer(modifier: Modifier = Modifier) {
    var ticks by remember { mutableStateOf(0) }
    LaunchedEffect(true) {
        while (true) {
            delay(1.seconds)
            ticks++
        }
    }
    Text(text = ticks.times(1000).convertDurationToString(), modifier = modifier)
}

@Composable
fun ReverseTimer(modifier: Modifier = Modifier, startTime: Int) {
    var ticks by remember { mutableStateOf(startTime.div(1000)) }
    LaunchedEffect(true) {
        while (ticks > 0) {
            delay(1.seconds)
            ticks--
        }
    }
    Text(text = ticks.times(1000).convertDurationToString(), modifier = modifier)
}
