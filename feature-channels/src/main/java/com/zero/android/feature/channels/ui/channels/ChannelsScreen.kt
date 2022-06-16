package com.zero.android.feature.channels.ui.channels

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.zero.android.common.ui.Result
import com.zero.android.models.Channel
import com.zero.android.models.ChannelCategory
import com.zero.android.models.Network
import com.zero.android.models.fake.FakeData
import com.zero.android.ui.extensions.Preview

@Composable
fun ChannelsRoute(network: Network?, viewModel: ChannelsViewModel = hiltViewModel()) {
	val categories: Result<List<ChannelCategory>> by viewModel.categories.collectAsState()
	val channels: Result<List<Channel>> by viewModel.channels.collectAsState()

	LaunchedEffect(network?.id) { network?.let { viewModel.onNetworkUpdated(it) } }

	ChannelsScreen(categories = categories, channels = channels)
}

@Composable
fun ChannelsScreen(categories: Result<List<ChannelCategory>>, channels: Result<List<Channel>>) {
	Column { Text(text = "Channels Screen") }
}

@Preview
@Composable
fun ChannelsScreenPreview() = Preview {
	ChannelsScreen(
		categories = Result.Success(listOf("One", "Two")),
		channels = Result.Success(FakeData.channels())
	)
}
