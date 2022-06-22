package com.zero.android.feature.channels.ui.channels

import androidx.lifecycle.viewModelScope
import com.zero.android.common.ui.Result
import com.zero.android.common.ui.asResult
import com.zero.android.common.ui.base.BaseViewModel
import com.zero.android.data.repository.ChannelRepository
import com.zero.android.models.ChannelCategory
import com.zero.android.models.GroupChannel
import com.zero.android.models.Network
import com.zero.android.models.fake.ChannelTab
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChannelsViewModel @Inject constructor(private val channelRepository: ChannelRepository) :
	BaseViewModel() {

	private lateinit var network: Network
	private val _categories = MutableStateFlow<Result<List<ChannelCategory>>>(Result.Loading)
	private val _channels = MutableStateFlow<Result<List<GroupChannel>>>(Result.Loading)

	val uiState: StateFlow<GroupChannelUiState> =
		combine(_categories, _channels) { categoriesResult, channelsResult ->
			if (categoriesResult is Result.Success && channelsResult is Result.Success) {
				val categories = categoriesResult.data
				val channels = channelsResult.data
				val channelGroups =
					channels.groupBy { if (it.category.isNullOrEmpty()) "Private" else it.category }
				val channelTabs =
					categories.map { category ->
						val unreadGroupMessagesCount =
							channelGroups[category]?.count { it.unreadMessageCount > 0 } ?: 0
						ChannelTab(category.hashCode().toLong(), category, unreadGroupMessagesCount)
					}
				GroupChannelUiState(
					ChannelCategoriesUiState.Success(channelTabs),
					CategoryChannelsUiState.Success(channels)
				)
			} else if (categoriesResult is Result.Loading) {
				GroupChannelUiState(ChannelCategoriesUiState.Loading, CategoryChannelsUiState.Loading)
			} else {
				GroupChannelUiState(ChannelCategoriesUiState.Error, CategoryChannelsUiState.Error)
			}
		}
			.stateIn(
				scope = viewModelScope,
				started = SharingStarted.WhileSubscribed(1_000),
				initialValue =
				GroupChannelUiState(
					ChannelCategoriesUiState.Loading,
					CategoryChannelsUiState.Loading
				)
			)

	fun onNetworkUpdated(network: Network) {
		this.network = network
		loadCategories()
		loadChannels()
	}

	private fun loadCategories() {
		ioScope.launch {
			channelRepository.getCategories(network.id).asResult().collectLatest { _categories.emit(it) }
		}
	}

	private fun loadChannels() {
		ioScope.launch {
			channelRepository.getGroupChannels(network.id).asResult().collectLatest { _channels.emit(it) }
		}
	}
}
