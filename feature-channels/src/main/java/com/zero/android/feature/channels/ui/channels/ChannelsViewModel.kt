package com.zero.android.feature.channels.ui.channels

import androidx.lifecycle.viewModelScope
import com.zero.android.common.ui.Result
import com.zero.android.common.ui.asResult
import com.zero.android.common.ui.base.BaseViewModel
import com.zero.android.data.manager.ChannelTriggerSearchManager
import com.zero.android.data.repository.ChannelRepository
import com.zero.android.data.repository.NetworkRepository
import com.zero.android.feature.channels.model.ChannelTab
import com.zero.android.feature.channels.ui.directchannels.DirectChannelScreenUiState
import com.zero.android.feature.channels.ui.directchannels.DirectChannelUiState
import com.zero.android.models.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChannelsViewModel
@Inject
constructor(
    private val networkRepository: NetworkRepository,
    private val channelRepository: ChannelRepository,
    private val channelSearchManager: ChannelTriggerSearchManager,
) : BaseViewModel() {

	private lateinit var network: Network
	private val _categories = MutableStateFlow<Result<List<ChannelCategory>>>(Result.Loading)
	private val _channels = MutableStateFlow<Result<List<GroupChannel>>>(Result.Loading)
	private val _filteredChannels = MutableStateFlow<Result<List<GroupChannel>>>(Result.Success(emptyList()))

    val showSearchBar: StateFlow<Boolean> = channelSearchManager.showSearchBar
    val uiState: StateFlow<GroupChannelUiState> =
		combine(_categories, _channels, _filteredChannels) { categoriesResult, channelsResult, filteredChannelResult ->
			if (categoriesResult is Result.Success && channelsResult is Result.Success) {
				val categories =
					mutableListOf<String>().apply {
						if (categoriesResult.data.isNotEmpty()) {
							add("All")
						}
						addAll(categoriesResult.data)
					}
				val channels = channelsResult.data
				val channelGroups = channels.groupBy { it.category }
				val channelTabs =
					categories.map { category ->
						val unreadGroupMessagesCount =
							when {
								category.equals("All", true) -> {
									channels.count { it.unreadMessageCount > 0 }
								}
								else -> channelGroups[category]?.count { it.unreadMessageCount > 0 } ?: 0
							}
						ChannelTab(category.hashCode().toLong(), category, unreadGroupMessagesCount)
					}
				GroupChannelUiState(
					ChannelCategoriesUiState.Success(channelTabs),
					if (filteredChannelResult is Result.Success && filteredChannelResult.data.isNotEmpty()) {
                        CategoryChannelsUiState.Success(filteredChannelResult.data, true)
                    } else {
                        CategoryChannelsUiState.Success(channels)
                    }
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

    fun filterChannels(query: String){
        ioScope.launch {
            val mainUiState = _channels.firstOrNull()
            if (mainUiState is Result.Success) {
                if (query.isEmpty()) {
                    _filteredChannels.emit(Result.Success(emptyList()))
                } else {
                    val filteredList = mainUiState.data.filter { it.getTitle().contains(query, true) }
                    _filteredChannels.emit(Result.Success(filteredList))
                }
            }
        }
    }

    fun onSearchClosed() {
        filterChannels("")
        ioScope.launch { channelSearchManager.triggerChannelSearch(false) }
    }

	private fun loadCategories() {
		ioScope.launch {
			networkRepository.getCategories(network.id).asResult().collectLatest { _categories.emit(it) }
		}
	}

	private fun loadChannels() {
		ioScope.launch {
			channelRepository.getGroupChannels(network.id).asResult().collectLatest { _channels.emit(it) }
		}
	}
}
