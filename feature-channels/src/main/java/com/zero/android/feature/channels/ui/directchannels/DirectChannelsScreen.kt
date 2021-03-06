package com.zero.android.feature.channels.ui.directchannels

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zero.android.common.R
import com.zero.android.feature.channels.ui.channels.ChannelsItemsList
import com.zero.android.models.Channel
import com.zero.android.models.Network
import com.zero.android.ui.components.CustomTextField
import com.zero.android.ui.extensions.Preview
import com.zero.android.ui.theme.AppTheme
import com.zero.android.ui.theme.Typography

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
	var searchText: String by remember { mutableStateOf("") }

	if (directChannelsUiState is DirectChannelUiState.Success) {
		Column(modifier = Modifier.fillMaxWidth()) {
			if (showSearchBar) {
				Row(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
					CustomTextField(
						value = searchText,
						onValueChange = {
							searchText = it
							onChannelSearched(searchText)
						},
						placeholderText = stringResource(R.string.search_channels),
						textStyle = Typography.bodyMedium.copy(color = AppTheme.colors.colorTextPrimary),
						shape = RoundedCornerShape(24.dp),
						modifier = Modifier.weight(1f),
						leadingIcon = {
							Icon(
								painterResource(R.drawable.ic_search),
								contentDescription = "",
								tint = AppTheme.colors.surfaceVariant
							)
						},
						trailingIcon = {
							IconButton(
								onClick = {
									searchText = ""
									onChannelSearched(searchText)
								}
							) {
								Icon(
									painter = painterResource(R.drawable.ic_cancel_24),
									contentDescription = "",
									tint = AppTheme.colors.surfaceVariant
								)
							}
						}
					)
					TextButton(
						onClick = {
							searchText = ""
							onSearchClosed()
						}
					) { Text("Cancel", color = AppTheme.colors.colorTextPrimary) }
				}
			}
			LazyColumn(modifier = Modifier.weight(1f)) {
				items(directChannelsUiState.channels) { channel ->
					ChannelsItemsList(loggedInUser, channel) { onChannelSelected(it) }
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
