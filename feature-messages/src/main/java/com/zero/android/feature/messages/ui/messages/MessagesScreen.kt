package com.zero.android.feature.messages.ui.messages

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MessagesRoute(viewModel: MessagesViewModel = hiltViewModel()) {
	MessagesScreen()
}

@Composable
fun MessagesScreen() {
	Column { Text(text = "Messages Screen") }
}
