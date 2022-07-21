package com.zero.android.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.zero.android.common.extensions.convertDurationToString
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun Timer(modifier: Modifier = Modifier, fontSize: TextUnit = 14.sp) {
	var ticks by remember { mutableStateOf(0) }
	LaunchedEffect(true) {
		while (true) {
			delay(1.seconds)
			ticks++
		}
	}
	Text(text = ticks.times(1000).convertDurationToString(), modifier = modifier, fontSize = fontSize)
}

@Composable
fun ReverseTimer(modifier: Modifier = Modifier, startTime: Int, fontSize: TextUnit = 14.sp) {
	var ticks by remember { mutableStateOf(startTime.div(1000)) }
	LaunchedEffect(true) {
		while (ticks > 0) {
			delay(1.seconds)
			ticks--
		}
	}
	Text(text = ticks.times(1000).convertDurationToString(), modifier = modifier, fontSize = fontSize)
}
