package com.zero.android.data.repository

import com.zero.android.data.conversion.toModel
import com.zero.android.models.Channel
import com.zero.android.models.OpenChannel
import com.zero.android.models.enums.ChannelType
import com.zero.android.network.model.ApiGroupChannel
import com.zero.android.network.model.ApiOpenChannel
import com.zero.android.network.service.ChannelService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChannelRepositoryImpl @Inject constructor(private val channelService: ChannelService) :
	ChannelRepository {

	override suspend fun getOpenChannels(networkId: String): Flow<List<OpenChannel>> {
		return channelService.getChannels(networkId, ChannelType.OPEN).map { channels ->
			channels.map { (it as ApiOpenChannel).toModel() }
		}
	}

	override suspend fun getGroupChannels(networkId: String): Flow<List<Channel>> {
		return channelService.getChannels(networkId, ChannelType.GROUP).map { channels ->
			channels.map { (it as ApiGroupChannel).toModel() }
		}
	}
}
