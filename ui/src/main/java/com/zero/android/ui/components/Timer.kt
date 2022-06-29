package com.zero.android.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit
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
    Text(text = convertDurationToString(ticks.times(1000)), modifier = modifier)
}

private fun convertDurationToString(duration: Int): String = String.format(
    "%02d:%02d",
    TimeUnit.MILLISECONDS.toMinutes(duration.toLong()),
    TimeUnit.MILLISECONDS.toSeconds(duration.toLong()) % TimeUnit.MINUTES.toSeconds(1)
)
