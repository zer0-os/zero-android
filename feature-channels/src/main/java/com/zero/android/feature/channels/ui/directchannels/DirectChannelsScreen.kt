package com.zero.android.feature.channels.ui.directchannels

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.zero.android.common.ui.Result
import com.zero.android.feature.channels.ui.channels.ChannelsItemsList
import com.zero.android.models.Channel
import com.zero.android.models.DirectChannel
import com.zero.android.models.Network
import com.zero.android.ui.extensions.Preview

@Composable
fun DirectChannelsRoute(
	network: Network?,
	viewModel: DirectChannelsViewModel = hiltViewModel(),
	onChannelSelected: (Channel) -> Unit
) {
	val channels: Result<List<DirectChannel>> by viewModel.channels.collectAsState()

	LaunchedEffect(network?.id) { network?.let { viewModel.onNetworkUpdated(it) } }
	DirectChannelsScreen(
		loggedInUser = viewModel.loggedInUserId,
		channels = channels,
		onChannelSelected = onChannelSelected
	)
}

@Composable
fun DirectChannelsScreen(
	loggedInUser: String,
	channels: Result<List<DirectChannel>>,
	onChannelSelected: (Channel) -> Unit
) {
	val directChannelUiState = DirectChannelUiState(channels)
	val directChannels = directChannelUiState.directChannels

	if (directChannels.isNotEmpty()) {
		Column(modifier = Modifier.fillMaxWidth()) {
			LazyColumn {
				items(directChannels) { channel ->
					ChannelsItemsList(loggedInUser, channel) { onChannelSelected(it) }
				}
			}
		}
	}
}

@Preview @Composable
fun DirectChannelsScreenPreview() = Preview {}
