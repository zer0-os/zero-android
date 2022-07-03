package com.zero.android.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zero.android.ui.theme.AppTheme
import kotlinx.coroutines.delay

@Composable
fun DotsBounceAnimation(
	modifier: Modifier = Modifier,
	size: Dp = 16.dp,
	color: Color = AppTheme.colors.glow
) {
	val dots =
		listOf(
			remember { Animatable(0.15f) },
			remember { Animatable(0.15f) },
			remember { Animatable(0.15f) },
			remember { Animatable(0.15f) },
			remember { Animatable(0.15f) },
			remember { Animatable(0.15f) },
			remember { Animatable(0.15f) },
			remember { Animatable(0.15f) },
			remember { Animatable(0.15f) },
			remember { Animatable(0.15f) },
			remember { Animatable(0.15f) },
			remember { Animatable(0.15f) },
			remember { Animatable(0.15f) },
			remember { Animatable(0.15f) },
			remember { Animatable(0.15f) }
		)
	dots.forEachIndexed { index, animatable ->
		LaunchedEffect(animatable) {
			delay(index * 200L)
			animatable.animateTo(
				targetValue = 1f,
				animationSpec =
				infiniteRepeatable(
					animation = tween(600, easing = FastOutLinearInEasing),
					repeatMode = RepeatMode.Reverse
				)
			)
		}
	}

	val dys = dots.map { it.value }
	Row(
		modifier = modifier,
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.Center
	) {
		dys.forEachIndexed { index, dy ->
			Box(Modifier.size(size).scale(dy).alpha(dy).background(color = color, shape = CircleShape))
		}
	}
}
