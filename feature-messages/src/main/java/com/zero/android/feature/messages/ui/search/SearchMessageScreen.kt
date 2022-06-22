package com.zero.android.feature.messages.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SearchMessageRoute(viewModel: SearchMessageViewModel = hiltViewModel()) {
    SearchMessageScreen()
}

@Composable
fun SearchMessageScreen() {
    Column { Text(text = "Search Message Screen") }
}
