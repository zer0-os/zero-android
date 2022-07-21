package com.zero.android.feature.channels.ui.channels

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.zero.android.common.R
import com.zero.android.models.Channel
import com.zero.android.models.GroupChannel
import com.zero.android.models.getTitle
import com.zero.android.ui.components.NameInitialsView
import com.zero.android.ui.theme.AppTheme

@Composable
fun ChannelSearchResult(channels: List<Channel>, onClick: (Channel) -> Unit) {
	val categorisedChannels =
		(channels as List<GroupChannel>).groupBy {
			if (it.category.isNullOrEmpty()) "Other" else it.category
		}
	Column(modifier = Modifier.fillMaxSize()) {
		Column(modifier = Modifier.fillMaxWidth().weight(1f)) {
			categorisedChannels.forEach { entry ->
				entry.key?.let { ChannelSearchItem(it, entry.value, onClick) }
			}
		}
		Text(
			text = "${channels.size} results found",
			modifier =
			Modifier.fillMaxWidth()
				.padding(vertical = 10.dp)
				.background(MaterialTheme.colorScheme.primary),
			textAlign = TextAlign.Center
		)
	}
}

@Composable
fun ChannelSearchItem(header: String, channels: List<GroupChannel>, onClick: (Channel) -> Unit) {
	Column(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically
		) {
			Text(
				header,
				color = AppTheme.colors.colorTextPrimary,
				style = MaterialTheme.typography.bodyMedium
			)
			Text(
				"${channels.size} found",
				color = AppTheme.colors.colorTextSecondary,
				style = MaterialTheme.typography.labelLarge
			)
		}
		Spacer(modifier = Modifier.size(8.dp))
		LazyRow(modifier = Modifier.fillMaxWidth()) {
			items(
				channels,
				itemContent = { channel ->
					Row(
						verticalAlignment = Alignment.CenterVertically,
						modifier = Modifier.clickable { onClick(channel) }
					) {
						NameInitialsView(modifier = Modifier.size(32.dp), userName = channel.getTitle())
						Spacer(modifier = Modifier.size(8.dp))
						Text(
							text = channel.getTitle(),
							color = AppTheme.colors.colorTextPrimary,
							style = MaterialTheme.typography.bodyMedium,
							maxLines = 1,
							overflow = TextOverflow.Ellipsis
						)
						if (channel.hasTelegramChannel) {
							Spacer(modifier = Modifier.padding(8.dp))
							Image(
								painter = painterResource(R.drawable.ic_vector),
								contentDescription = "",
								modifier = Modifier.wrapContentSize().align(Alignment.CenterVertically),
								contentScale = ContentScale.Fit
							)
						}
						if (channel.hasDiscordChannel) {
							Spacer(modifier = Modifier.padding(8.dp))
							Image(
								painter = painterResource(R.drawable.ic_discord),
								contentDescription = "",
								modifier = Modifier.wrapContentSize().align(Alignment.CenterVertically),
								contentScale = ContentScale.Fit
							)
						}
					}
				}
			)
		}
	}
}
