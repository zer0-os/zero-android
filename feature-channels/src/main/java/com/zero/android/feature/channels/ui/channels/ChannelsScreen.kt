package com.zero.android.feature.channels.ui.channels

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.zero.android.models.Channel
import com.zero.android.models.Network
import com.zero.android.ui.extensions.Preview

@Composable
fun ChannelsRoute(
	network: Network?,
	viewModel: ChannelsViewModel = hiltViewModel(),
	onChannelSelected: (Channel) -> Unit
) {
	val uiState: GroupChannelUiState by viewModel.uiState.collectAsState()
	LaunchedEffect(network?.id) { network?.let { viewModel.onNetworkUpdated(it) } }

	ChannelsScreen(uiState, onChannelSelected)
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ChannelsScreen(uiState: GroupChannelUiState, onChannelSelected: (Channel) -> Unit) {
	val pagerState = rememberPagerState(initialPage = 0)
	val coroutineScope = rememberCoroutineScope()

	if (uiState.categoriesUiState is ChannelCategoriesUiState.Success &&
		uiState.categoryChannelsUiState is CategoryChannelsUiState.Success
	) {
		val tabs = uiState.categoriesUiState.categories
		if (tabs.isNotEmpty()) {
			Column(modifier = Modifier.fillMaxWidth()) {
				ChannelTabLayout(pagerState = pagerState, coroutineScope = coroutineScope, tabs = tabs)
				ChannelPager(pagerState = pagerState, groupChannelUiState = uiState) {
					onChannelSelected(it)
				}
			}
		}
	}
}

@Preview @Composable
fun ChannelsScreenPreview() = Preview {}
