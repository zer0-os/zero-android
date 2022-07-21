package com.zero.android.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ExtraSmallCircularImage(
	modifier: Modifier = Modifier,
	imageUrl: String? = null,
	@DrawableRes placeHolder: Int,
	contentDescription: String = ""
) {
	AsyncImage(
		model = imageUrl,
		placeholder = painterResource(placeHolder),
		error = painterResource(placeHolder),
		contentDescription = contentDescription,
		modifier = modifier.size(24.dp).clip(CircleShape)
	)
}

@Composable
fun SmallCircularImage(
	modifier: Modifier = Modifier,
	imageUrl: String? = null,
	@DrawableRes placeHolder: Int,
	contentDescription: String = ""
) {
	AsyncImage(
		model = imageUrl,
		placeholder = painterResource(placeHolder),
		error = painterResource(placeHolder),
		contentDescription = contentDescription,
		modifier = modifier.size(36.dp).clip(CircleShape)
	)
}

@Composable
fun MediumCircularImage(
	modifier: Modifier = Modifier,
	imageUrl: String? = null,
	@DrawableRes placeHolder: Int,
	contentDescription: String = ""
) {
	AsyncImage(
		model = imageUrl,
		placeholder = painterResource(placeHolder),
		error = painterResource(placeHolder),
		contentDescription = contentDescription,
		modifier = modifier.size(42.dp).clip(CircleShape)
	)
}

@Composable
fun BigCircularImage(
	modifier: Modifier = Modifier,
	imageUrl: String? = null,
	@DrawableRes placeHolder: Int,
	contentDescription: String = ""
) {
	AsyncImage(
		model = imageUrl,
		placeholder = painterResource(placeHolder),
		error = painterResource(placeHolder),
		contentDescription = contentDescription,
		modifier = modifier.size(54.dp).clip(CircleShape)
	)
}

@Composable
fun LargeCircularImage(
	modifier: Modifier = Modifier,
	imageUrl: String? = null,
	@DrawableRes placeHolder: Int,
	contentDescription: String = ""
) {
	AsyncImage(
		model = imageUrl,
		placeholder = painterResource(placeHolder),
		error = painterResource(placeHolder),
		contentDescription = contentDescription,
		modifier = modifier.size(64.dp).clip(CircleShape)
	)
}
