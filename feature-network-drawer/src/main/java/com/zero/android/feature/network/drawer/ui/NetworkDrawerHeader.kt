package com.zero.android.feature.network.drawer.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.zero.android.feature.network.drawer.R
import com.zero.android.feature.network.drawer.misc.NetworkWorld
import com.zero.android.ui.theme.ZeroExtendedTheme

@Composable
fun NetworkDrawerHeader(
	modifier: Modifier = Modifier,
	item: NetworkWorld,
	onSettingsClick: () -> Unit,
	onInviteClick: () -> Unit
) {
	ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
		val (imageStart, textTop, textBottom, imageEnd, inviteButton, divider) = createRefs()

		Spacer(modifier = Modifier.fillMaxSize().padding(12.dp))
		Image(
			painter = rememberAsyncImagePainter(item.icon),
			contentDescription = item.title,
			contentScale = ContentScale.Fit,
			modifier =
			Modifier.constrainAs(imageStart) {
				top.linkTo(parent.top)
				bottom.linkTo(inviteButton.top)
				start.linkTo(parent.start)
				end.linkTo(textTop.start)
			}
				.size(42.dp)
				.clip(CircleShape)
		)
		Text(
			text = item.title,
			modifier =
			Modifier.constrainAs(textTop) {
				top.linkTo(parent.top)
				bottom.linkTo(textBottom.top)
				start.linkTo(imageStart.end)
				end.linkTo(imageEnd.start)
			},
			color = ZeroExtendedTheme.colors.colorTextPrimary,
			fontSize = 20.sp
		)
		Text(
			text = item.domain,
			modifier =
			Modifier.constrainAs(textBottom) {
				top.linkTo(textTop.bottom)
				bottom.linkTo(inviteButton.top)
				start.linkTo(textTop.start)
				end.linkTo(textTop.end)
			},
			color = ZeroExtendedTheme.colors.colorTextSecondary
		)
		Image(
			painter = painterResource(R.drawable.ic_settings),
			contentDescription = stringResource(R.string.cd_ic_settings),
			contentScale = ContentScale.Fit,
			modifier =
			Modifier.constrainAs(imageEnd) {
				top.linkTo(parent.top)
				bottom.linkTo(inviteButton.top)
				start.linkTo(parent.start)
				end.linkTo(textTop.start)
			}
				.wrapContentSize()
				.clickable(onClick = onSettingsClick)
		)
		OutlinedButton(
			onClick = onInviteClick,
			modifier =
			Modifier.constrainAs(inviteButton) {
				top.linkTo(imageStart.bottom, margin = 16.dp)
				start.linkTo(imageStart.start)
				bottom.linkTo(divider.top)
			},
			border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
			shape = RoundedCornerShape(24.dp),
			colors =
			ButtonDefaults.outlinedButtonColors(
				contentColor = ZeroExtendedTheme.colors.colorTextPrimary
			)
		) {
			Text(
				text = stringResource(R.string.invite_members),
				style =
				TextStyle(
					shadow =
					Shadow(
						color = MaterialTheme.colorScheme.primary,
						offset = Offset(2f, 2f),
						blurRadius = 10f
					)
				),
				fontSize = 16.sp,
				fontWeight = FontWeight.Medium
			)
		}
		Divider(
			color = ZeroExtendedTheme.colors.buttonSecondary,
			modifier =
			Modifier.constrainAs(divider) {
				start.linkTo(parent.start)
				end.linkTo(parent.end)
				bottom.linkTo(parent.bottom)
				top.linkTo(inviteButton.bottom, margin = 12.dp)
			}
				.fillMaxWidth(),
			thickness = 1.dp
		)
	}
}

@Preview @Composable
fun NetworkDrawerHeaderPreview() {}
