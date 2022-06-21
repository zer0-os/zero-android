package com.zero.android.feature.channels.ui.channels

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.zero.android.common.ui.Result
import com.zero.android.models.Channel
import com.zero.android.models.ChannelCategory
import com.zero.android.models.Network
import com.zero.android.models.fake.FakeData
import com.zero.android.ui.extensions.Preview

@Composable
fun ChannelsRoute(network: Network?,
                  viewModel: ChannelsViewModel = hiltViewModel(),
                  onChannelSelected:(Channel) -> Unit,
) {
	val categories: Result<List<ChannelCategory>> by viewModel.categories.collectAsState()
	val channels: Result<List<Channel>> by viewModel.channels.collectAsState()

	LaunchedEffect(network?.id) { network?.let { viewModel.onNetworkUpdated(it) } }

	ChannelsScreen(categories = categories, channels = channels, onChannelSelected)
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ChannelsScreen(
    categories: Result<List<ChannelCategory>>,
    channels: Result<List<Channel>>,
    onChannelSelected:(Channel) -> Unit,
) {
    val groupChannelUiState = GroupChannelUiState(categories, channels)
    val pagerState = rememberPagerState(
        initialPage = 0,
    )
    val coroutineScope = rememberCoroutineScope()
    val tabs = groupChannelUiState.channelCategories

    if (tabs.isNotEmpty()) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            ChannelTabLayout(
                pagerState = pagerState,
                coroutineScope = coroutineScope,
                tabs = tabs
            )
            ChannelPager(
                pagerState = pagerState,
                groupChannelUiState = groupChannelUiState
            ) {
                onChannelSelected(it)
            }
        }
    }
}

@Preview
@Composable
fun ChannelsScreenPreview() = Preview {
	ChannelsScreen(
		categories = Result.Success(listOf("One", "Two")),
		channels = Result.Success(FakeData.groupChannels())
	){

    }
}
