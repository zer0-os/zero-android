package com.zero.android.feature.channel.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.zero.android.feature.channel.misc.CType
import com.zero.android.ui.theme.ZeroExtendedTheme

@Composable
fun HashTagRow(modifier: Modifier = Modifier, ctype: CType, onItemClick: () -> Unit) {
	ConstraintLayout(modifier = Modifier.fillMaxWidth().clickable { onItemClick.invoke() }) {
		val (image, textTop, textBottom, textEnd, time) = createRefs()

		Spacer(modifier = Modifier.fillMaxSize().padding(12.dp))
		Image(
			// painter = rememberAsyncImagePainter(),
			contentDescription = "cd_image",
			contentScale = ContentScale.Fit,
			modifier =
			Modifier.constrainAs(image) {
				top.linkTo(parent.top)
				bottom.linkTo(parent.bottom)
				start.linkTo(parent.start)
				end.linkTo(textTop.start)
			}
				.size(64.dp)
				.clip(CircleShape)
		)
		Text(
			text = "Game Dev",
			modifier =
			Modifier.constrainAs(textTop) {
				top.linkTo(image.top)
				bottom.linkTo(textBottom.top)
				start.linkTo(image.end)
				end.linkTo(textEnd.start)
			}
				.wrapContentSize(Alignment.CenterStart)
				.padding(horizontal = 12.dp),
			color = ZeroExtendedTheme.colors.colorTextPrimary,
			fontSize = 16.sp
		)
		Text(
			text = "Lefty Wilder: Uploaded an image.",
			modifier =
			Modifier.constrainAs(textBottom) {
				top.linkTo(textTop.bottom)
				start.linkTo(textTop.start)
				end.linkTo(textEnd.start)
			},
			color = ZeroExtendedTheme.colors.colorTextSecondary
		)
		Text(
			text = "Tue",
			modifier =
			Modifier.constrainAs(time) {
				top.linkTo(image.top)
				end.linkTo(parent.end)
			}
				.padding(horizontal = 6.dp),
			color = ZeroExtendedTheme.colors.colorTextSecondary,
			fontSize = 12.sp,
			fontWeight = FontWeight.Medium
		)
		val showCount = true
		if (showCount) {
			Text(
				text = "25",
				modifier =
				Modifier.constrainAs(textEnd) {
					bottom.linkTo(image.bottom)
					end.linkTo(time.end)
				}
					.background(MaterialTheme.colorScheme.primary, RoundedCornerShape(30.dp))
					.padding(horizontal = 6.dp, vertical = 2.dp),
				color = ZeroExtendedTheme.colors.colorTextSecondary,
				fontSize = 12.sp,
				fontWeight = FontWeight.Medium
			)
		}
	}
}

@Preview @Composable
fun HashTagRowPreview() {}
