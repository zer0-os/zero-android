package com.zero.android.feature.channels.ui.directchannels

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zero.android.common.R
import com.zero.android.feature.channels.ui.components.ChannelListItem
import com.zero.android.models.Channel
import com.zero.android.models.Network
import com.zero.android.ui.components.SearchView
import com.zero.android.ui.extensions.Preview

@Composable
fun DirectChannelsRoute(
	network: Network?,
	viewModel: DirectChannelsViewModel = hiltViewModel(),
	onChannelSelected: (Channel) -> Unit
) {
	val uiState: DirectChannelScreenUiState by viewModel.uiState.collectAsState()
	val showSearch: Boolean by viewModel.showSearchBar.collectAsState()

	LaunchedEffect(network?.id) { network?.let { viewModel.onNetworkUpdated(it) } }
	DirectChannelsScreen(
		loggedInUser = viewModel.loggedInUserId,
		uiState = uiState,
		showSearchBar = showSearch,
		onChannelSelected = onChannelSelected,
		onChannelSearched = { viewModel.filterChannels(it) },
		onSearchClosed = viewModel::onSearchClosed
	)
}

@Composable
fun DirectChannelsScreen(
	loggedInUser: String,
	uiState: DirectChannelScreenUiState,
	showSearchBar: Boolean = false,
	onChannelSelected: (Channel) -> Unit,
	onChannelSearched: (String) -> Unit,
	onSearchClosed: () -> Unit
) {
	val directChannelsUiState = uiState.directChannelsUiState

	if (directChannelsUiState is DirectChannelUiState.Success) {
		Column(modifier = Modifier.fillMaxWidth()) {
			if (showSearchBar) {
                SearchView(
                    placeHolder = stringResource(R.string.search_channels),
                    onValueChanged = { onChannelSearched(it) },
                    onSearchCancelled = { onSearchClosed() }
                )
			}
			LazyColumn(modifier = Modifier.weight(1f)) {
				items(directChannelsUiState.channels) { channel ->
					ChannelListItem(loggedInUser, channel) { onChannelSelected(it) }
				}
			}
			if (directChannelsUiState.isSearchResult) {
				Text(
					text = "${directChannelsUiState.channels.size} results found",
					modifier =
					Modifier.fillMaxWidth()
						.padding(vertical = 10.dp)
						.background(MaterialTheme.colorScheme.primary),
					textAlign = TextAlign.Center
				)
			}
		}
	}
}

@Preview @Composable
fun DirectChannelsScreenPreview() = Preview {}
