package com.zero.android.feature.channel.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ChannelRoute(
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier,
    viewModel: ChannelViewModel = hiltViewModel()
) {
    ChannelScreen(windowSizeClass = windowSizeClass, modifier = modifier)
}

@Composable
fun ChannelScreen(windowSizeClass: WindowSizeClass, modifier: Modifier = Modifier) {
    Column(modifier = Modifier.fillMaxSize()) {

    }
}
