package com.zero.android.feature.channels.ui.directchannels

import com.zero.android.common.ui.Result
import com.zero.android.common.ui.asResult
import com.zero.android.common.ui.base.BaseViewModel
import com.zero.android.common.usecases.SearchTriggerUseCase
import com.zero.android.data.delegates.Preferences
import com.zero.android.data.repository.ChannelRepository
import com.zero.android.models.Channel
import com.zero.android.models.Network
import com.zero.android.models.getTitle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class DirectChannelsViewModel
@Inject
constructor(
	private val preferences: Preferences,
	private val channelRepository: ChannelRepository,
	private val searchTriggerUseCase: SearchTriggerUseCase
) : BaseViewModel() {

	private lateinit var network: Network
	val loggedInUserId
		get() = runBlocking(Dispatchers.IO) { preferences.userId() }

	private val _uiState = MutableStateFlow(DirectChannelScreenUiState(DirectChannelUiState.Loading))
	val uiState: StateFlow<DirectChannelScreenUiState> = _uiState
	private val mainDataSource = mutableListOf<Channel>()
	val showSearchBar: StateFlow<Boolean> = searchTriggerUseCase.showSearchBar

	fun onNetworkUpdated(network: Network) {
		this.network = network
		loadChannels()
	}

	fun filterChannels(query: String) {
		ioScope.launch {
			val mainUiState = _uiState.firstOrNull()?.directChannelsUiState
			if (mainUiState is DirectChannelUiState.Success) {
				if (query.isEmpty()) {
					_uiState.emit(DirectChannelScreenUiState(DirectChannelUiState.Success(mainDataSource)))
				} else {
					val filteredList =
						mainDataSource.filter { it.getTitle(loggedInUserId).contains(query, true) }
					_uiState.emit(
						DirectChannelScreenUiState(DirectChannelUiState.Success(filteredList, true))
					)
				}
			}
		}
	}

	fun onSearchClosed() {
		filterChannels("")
		ioScope.launch { searchTriggerUseCase.triggerSearch(false) }
	}

	private fun loadChannels() {
		ioScope.launch {
			channelRepository.getDirectChannels().asResult().collectLatest {
				when (it) {
					is Result.Success -> {
						_uiState.emit(DirectChannelScreenUiState(DirectChannelUiState.Success(it.data)))
						mainDataSource.apply {
							clear()
							addAll(it.data)
						}
					}
					is Result.Loading ->
						_uiState.emit(DirectChannelScreenUiState(DirectChannelUiState.Loading))
					else -> _uiState.emit(DirectChannelScreenUiState(DirectChannelUiState.Error))
				}
			}
		}
	}
}
