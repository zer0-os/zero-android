package com.zero.android.feature.channels.ui.channels

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.zero.android.models.fake.FakeData
import com.zero.android.ui.extensions.Preview

@Composable
fun ChannelsRoute(viewModel: ChannelsViewModel = hiltViewModel()) {
    ChannelsScreen()
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ChannelsScreen() {
    val pagerState = rememberPagerState(
        initialPage = 0,
    )
    val coroutineScope = rememberCoroutineScope()
    val tabs = FakeData.channelTabs()
    val messages = FakeData.channelMessages()

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        ChannelTabLayout(
            pagerState = pagerState,
            coroutineScope = coroutineScope,
            tabs = tabs
        ) {

        }
        ChannelPager(
            pagerState = pagerState,
            tabs = tabs,
            messages = messages
        ) {

        }
    }
}

@Preview @Composable
fun ChannelScreenPreview() = Preview {
    ChannelsScreen()
}
