package com.zero.android.feature.messages.ui.message

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MessageRoute(viewModel: MessageViewModel = hiltViewModel()) {
	MessageScreen()
}

@Composable
fun MessageScreen() {
	Column { Text(text = "Message Screen") }
}
