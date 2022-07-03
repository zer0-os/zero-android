package com.zero.android.feature.channels.ui.directchannels

import com.zero.android.common.ui.Result
import com.zero.android.common.ui.asResult
import com.zero.android.common.ui.base.BaseViewModel
import com.zero.android.data.repository.ChannelRepository
import com.zero.android.database.AppPreferences
import com.zero.android.models.Network
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class DirectChannelsViewModel
@Inject
constructor(
	private val preferences: AppPreferences,
	private val channelRepository: ChannelRepository
) : BaseViewModel() {

	private lateinit var network: Network
	val loggedInUserId
		get() = runBlocking(Dispatchers.IO) { preferences.userId() }

	private val _uiState = MutableStateFlow(DirectChannelScreenUiState(DirectChannelUiState.Loading))
	val uiState: StateFlow<DirectChannelScreenUiState> = _uiState

	fun onNetworkUpdated(network: Network) {
		this.network = network
		loadChannels()
	}

	private fun loadChannels() {
		ioScope.launch {
			channelRepository.getDirectChannels().asResult().collectLatest {
				when (it) {
					is Result.Success ->
						_uiState.emit(DirectChannelScreenUiState(DirectChannelUiState.Success(it.data)))
					is Result.Loading ->
						_uiState.emit(DirectChannelScreenUiState(DirectChannelUiState.Loading))
					else -> _uiState.emit(DirectChannelScreenUiState(DirectChannelUiState.Error))
				}
			}
		}
	}
}
