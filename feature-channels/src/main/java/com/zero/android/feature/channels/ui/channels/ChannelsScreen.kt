package com.zero.android.feature.channels.ui.channels

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.zero.android.models.fake.FakeData
import com.zero.android.ui.extensions.Preview
import com.zero.android.common.ui.Result
import com.zero.android.models.ChannelCategory
import com.zero.android.models.GroupChannel
import com.zero.android.models.Network
import com.zero.android.models.fake.FakeData
import com.zero.android.ui.extensions.Preview

@Composable
fun ChannelsRoute(network: Network?, viewModel: ChannelsViewModel = hiltViewModel()) {
	val categories: Result<List<ChannelCategory>> by viewModel.categories.collectAsState()
	val channels: Result<List<GroupChannel>> by viewModel.channels.collectAsState()

	LaunchedEffect(network?.id) { network?.let { viewModel.onNetworkUpdated(it) } }

	ChannelsScreen(categories = categories, channels = channels)
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ChannelsScreen(
	categories: Result<List<ChannelCategory>>,
	channels: Result<List<GroupChannel>>
) {
	Column { Text(text = "Channels Screen") }
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

@Preview
@Composable
fun ChannelsScreenPreview() = Preview {
	ChannelsScreen(
		categories = Result.Success(listOf("One", "Two")),
		channels = Result.Success(FakeData.groupChannels())
	)
}
