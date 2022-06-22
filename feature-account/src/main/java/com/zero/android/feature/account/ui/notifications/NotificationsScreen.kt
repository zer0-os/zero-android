package com.zero.android.feature.account.ui.notifications

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun NotificationsRoute(viewModel: NotificationsViewModel = hiltViewModel()) {
	NotificationsScreen()
}

@Composable
fun NotificationsScreen() {
	Column { Text(text = "Notifications Screen") }
}
