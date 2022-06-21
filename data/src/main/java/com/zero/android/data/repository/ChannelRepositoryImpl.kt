package com.zero.android.data.repository

import com.zero.android.data.conversion.toModel
import com.zero.android.models.Channel
import com.zero.android.models.DirectChannel
import com.zero.android.models.GroupChannel
import com.zero.android.models.enums.ChannelType
import com.zero.android.network.model.ApiDirectChannel
import com.zero.android.network.model.ApiGroupChannel
import com.zero.android.network.service.ChannelService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChannelRepositoryImpl @Inject constructor(private val channelService: ChannelService) :
	ChannelRepository {

	override suspend fun getCategories(networkId: String) = channelService.getCategories(networkId)

	override suspend fun getDirectChannels(): Flow<List<DirectChannel>> {
		return channelService.getDirectChannels().map { channels -> channels.map { it.toModel() } }
	}

	override suspend fun getGroupChannels(networkId: String): Flow<List<GroupChannel>> {
		return channelService.getGroupChannels(networkId, ChannelType.GROUP).map { channels ->
			channels.map { it.toModel() }
		}
	}

	override suspend fun getGroupChannel(id: String): Flow<GroupChannel> {
		return channelService.getChannel(id, type = ChannelType.GROUP).map {
			(it as ApiGroupChannel).toModel()
		}
	}

	override suspend fun getDirectChannel(id: String): Flow<GroupChannel> {
		return channelService.getChannel(id, type = ChannelType.GROUP).map {
			(it as ApiGroupChannel).toModel()
		}
	}

	override suspend fun joinChannel(channel: Channel) = channelService.joinChannel(channel)

	override suspend fun deleteChannel(channel: Channel) = channelService.deleteChannel(channel)
}
