package com.zero.android.feature.channels.ui.channels

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.zero.android.common.R
import com.zero.android.models.Channel
import com.zero.android.models.Network
import com.zero.android.ui.components.CustomTextField
import com.zero.android.ui.extensions.Preview
import com.zero.android.ui.theme.AppTheme
import com.zero.android.ui.theme.Typography

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
	var searchText: String by remember { mutableStateOf("") }

	if (uiState.categoriesUiState is ChannelCategoriesUiState.Success &&
		uiState.categoryChannelsUiState is CategoryChannelsUiState.Success
	) {
		val tabs = uiState.categoriesUiState.categories
		val isSearchResult = uiState.categoryChannelsUiState.isSearchResult
		if (tabs.isNotEmpty()) {
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
