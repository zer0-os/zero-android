package com.zero.android.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun WaveAnimation(@DrawableRes iconResId: Int, iconTint: Color) {
	val waves =
		listOf(
			remember { Animatable(0f) },
			remember { Animatable(0f) },
			remember { Animatable(0f) },
			remember { Animatable(0f) }
		)
	val animationSpec =
		infiniteRepeatable<Float>(
			animation = tween(4000, easing = FastOutLinearInEasing),
			repeatMode = RepeatMode.Restart
		)
	waves.forEachIndexed { index, animatable ->
		LaunchedEffect(animatable) {
			delay(index * 1000L)
			animatable.animateTo(targetValue = 1f, animationSpec = animationSpec)
		}
	}

	val dys = waves.map { it.value }
	Box(modifier = Modifier.wrapContentSize()) {
		dys.forEach { dy ->
			Box(
				Modifier.size(34.dp).align(Center).graphicsLayer {
					scaleX = dy * 4 + 1
					scaleY = dy * 4 + 1
					alpha = 1 - dy
				}
			) { Box(Modifier.fillMaxSize().background(color = Color.White, shape = CircleShape)) }
		}
		// Center Icon
		Box(Modifier.size(26.dp).align(Center).background(color = Color.White, shape = CircleShape)) {
			Icon(
				painter = painterResource(iconResId),
				"",
				tint = iconTint,
				modifier = Modifier.size(18.dp).align(Center)
			)
		}
	}
}
