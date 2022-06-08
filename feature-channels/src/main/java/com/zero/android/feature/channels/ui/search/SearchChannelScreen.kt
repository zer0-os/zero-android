package com.zero.android.feature.channels.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SearchChannelScreen(viewModel: SearchChannelViewModel = hiltViewModel()) {
	SearchChannelScreen()
}

@Composable
fun SearchChannelScreen() {
	Column { Text(text = "Search Channel Screen") }
}
