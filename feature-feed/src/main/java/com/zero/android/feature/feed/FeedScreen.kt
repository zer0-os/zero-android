package com.zero.android.feature.feed

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun FeedRoute(viewModel: FeedViewModel = hiltViewModel()) {
	FeedScreen()
}

@Composable
fun FeedScreen() {
	Column { Text(text = "Feed Screen") }
}
