package com.zero.android.feature.messages.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.zero.android.common.R
import com.zero.android.models.Message
import com.zero.android.ui.components.ExtraSmallCircularImage
import com.zero.android.ui.theme.AppTheme
import com.zero.android.ui.theme.Typography

@Composable
fun ReplyMessage(
	modifier: Modifier = Modifier.fillMaxWidth(),
	message: Message,
	showCloseButton: Boolean = true,
	onCloseView: () -> Unit = {}
) {
	Surface(
		modifier = if (showCloseButton) modifier.padding(6.dp) else modifier,
		color = Color.Transparent
	) {
		Row(
			modifier =
			modifier
				.background(
					color = AppTheme.colors.surface.copy(alpha = 0.2f),
					shape = RoundedCornerShape(12.dp)
				)
				.padding(horizontal = 6.dp, vertical = 6.dp),
			verticalAlignment = Alignment.CenterVertically
		) {
			ExtraSmallCircularImage(
				imageUrl = message.author.profileImage,
				placeHolder = R.drawable.ic_user_profile_placeholder
			)
			Spacer(modifier = Modifier.size(8.dp))
			Column(modifier = Modifier.weight(1f)) {
				message.author.name?.let {
					Text(
						text = it,
						color = AppTheme.colors.colorTextPrimary,
						style = Typography.labelLarge,
						fontWeight = FontWeight.Medium,
						maxLines = 1,
						overflow = TextOverflow.Ellipsis
					)
					Spacer(modifier = Modifier.size(2.dp))
				}
				val replyMessage =
					if (message.message != null) {
						message.message!!
					} else "${message.type.name} Message"
				Text(
					text = replyMessage,
					color = AppTheme.colors.colorTextSecondary,
					style = Typography.labelLarge,
					fontWeight = FontWeight.Normal,
					maxLines = 2,
					overflow = TextOverflow.Ellipsis
				)
			}
			Spacer(modifier = Modifier.size(8.dp))
			message.fileUrl?.let {
				AsyncImage(model = it, contentDescription = "", modifier = Modifier.size(60.dp))
				Spacer(modifier = Modifier.size(8.dp))
			}
			if (showCloseButton) {
				IconButton(onClick = onCloseView) {
					Icon(imageVector = Icons.Filled.Close, contentDescription = "", tint = Color.DarkGray)
				}
			}
		}
	}
}
