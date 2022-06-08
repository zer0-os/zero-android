package com.zero.android.feature.channels.ui.channels

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ChannelsRoute(viewModel: ChannelsViewModel = hiltViewModel()) {
	ChannelsScreen()
}

@Composable
fun ChannelsScreen() {
	Column { Text(text = "Channels Screen") }
}
