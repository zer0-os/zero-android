package com.zero.android.feature.channels.ui.channels

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.zero.android.feature.channels.ui.components.ChannelListItem
import com.zero.android.models.Channel

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ChannelPager(
	pagerState: PagerState,
	groupChannelUiState: GroupChannelUiState,
	onClick: (Channel) -> Unit
) {
	val categories =
		(groupChannelUiState.categoriesUiState as ChannelCategoriesUiState.Success).categories
	HorizontalPager(state = pagerState, count = categories.size) { index ->
		Column(modifier = Modifier.fillMaxSize()) {
			LazyColumn {
				items(groupChannelUiState.getChannels(categories[index].name)) { channel ->
                    ChannelListItem(channel = channel, onClick = onClick)
				}
			}
		}
	}
}
