package com.zero.android.feature.channels.ui.directchannels

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.zero.android.common.ui.Result
import com.zero.android.models.DirectChannel
import com.zero.android.models.fake.FakeData
import com.zero.android.ui.extensions.Preview

@Composable
fun DirectChannelsRoute(viewModel: DirectChannelsViewModel = hiltViewModel()) {
	val channels: Result<List<DirectChannel>> by viewModel.channels.collectAsState()

	DirectChannelsScreen(channels = channels)
}

@Composable
fun DirectChannelsScreen(channels: Result<List<DirectChannel>>) {
	Column { Text(text = "Direct Channels Screen") }
}

@Preview
@Composable
fun DirectChannelsScreenPreview() = Preview {
	DirectChannelsScreen(channels = Result.Success(FakeData.directChannels()))
}
