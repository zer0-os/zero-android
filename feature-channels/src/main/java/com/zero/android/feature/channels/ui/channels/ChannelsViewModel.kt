package com.zero.android.feature.channels.ui.channels

import com.zero.android.common.ui.Result
import com.zero.android.common.ui.asResult
import com.zero.android.common.ui.base.BaseViewModel
import com.zero.android.data.repository.ChannelRepository
import com.zero.android.models.ChannelCategory
import com.zero.android.models.GroupChannel
import com.zero.android.models.Network
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChannelsViewModel @Inject constructor(private val channelRepository: ChannelRepository) :
	BaseViewModel() {

	private lateinit var network: Network
	val categories = MutableStateFlow<Result<List<ChannelCategory>>>(Result.Loading)
	val channels = MutableStateFlow<Result<List<GroupChannel>>>(Result.Loading)

	fun onNetworkUpdated(network: Network) {
		this.network = network
		loadCategories()
		loadChannels()
	}

	private fun loadCategories() {
		ioScope.launch {
			channelRepository.getCategories(network.id).asResult().collectLatest { categories.emit(it) }
		}
	}

	private fun loadChannels() {
		ioScope.launch {
			channelRepository.getGroupChannels(network.id).asResult().collectLatest {
				channels.emit(it)
			}
		}
	}
}
