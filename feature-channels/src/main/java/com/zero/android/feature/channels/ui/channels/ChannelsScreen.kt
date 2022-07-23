package com.zero.android.feature.channels.ui.channels

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.zero.android.common.R
import com.zero.android.models.Channel
import com.zero.android.models.Network
import com.zero.android.ui.components.SearchView
import com.zero.android.ui.extensions.Preview

@Composable
fun ChannelsRoute(
	network: Network?,
	viewModel: ChannelsViewModel = hiltViewModel(),
	onChannelSelected: (Channel) -> Unit
) {
	val uiState: GroupChannelUiState by viewModel.uiState.collectAsState()
	val showSearch: Boolean by viewModel.showSearchBar.collectAsState()

	LaunchedEffect(network?.id) { network?.let { viewModel.onNetworkUpdated(it) } }
	ChannelsScreen(
		uiState,
		showSearch,
		onChannelSelected,
		onChannelSearched = { viewModel.filterChannels(it) },
		onSearchClosed = viewModel::onSearchClosed
	)
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ChannelsScreen(
	uiState: GroupChannelUiState,
	showSearchBar: Boolean = false,
	onChannelSelected: (Channel) -> Unit,
	onChannelSearched: (String) -> Unit,
	onSearchClosed: () -> Unit
) {
	val coroutineScope = rememberCoroutineScope()
	val pagerState = rememberPagerState(initialPage = 0)

	if (uiState.categoriesUiState is ChannelCategoriesUiState.Success &&
		uiState.categoryChannelsUiState is CategoryChannelsUiState.Success
	) {
		val tabs = uiState.categoriesUiState.categories
		val isSearchResult = uiState.categoryChannelsUiState.isSearchResult
		if (tabs.isNotEmpty()) {
			Column(modifier = Modifier.fillMaxWidth()) {
				if (showSearchBar) {
                    SearchView(
                        placeHolder = stringResource(R.string.search_channels),
                        onValueChanged = { onChannelSearched(it) },
                        onSearchCancelled = { onSearchClosed() }
                    )
				}
				if (isSearchResult) {
					ChannelSearchResult(uiState.categoryChannelsUiState.channels) {
						onSearchClosed()
						onChannelSelected(it)
					}
				} else {
					ChannelTabLayout(pagerState = pagerState, coroutineScope = coroutineScope, tabs = tabs)
					ChannelPager(pagerState = pagerState, groupChannelUiState = uiState) {
						onChannelSelected(it)
					}
				}
			}
		}
	}
}

@Preview @Composable
fun ChannelsScreenPreview() = Preview {}
