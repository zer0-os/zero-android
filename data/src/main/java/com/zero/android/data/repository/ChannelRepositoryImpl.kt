package com.zero.android.data.repository

import com.zero.android.data.conversion.toEntity
import com.zero.android.data.conversion.toModel
import com.zero.android.database.dao.ChannelDao
import com.zero.android.database.model.ChannelEntity
import com.zero.android.database.model.toModel
import com.zero.android.models.Channel
import com.zero.android.models.DirectChannel
import com.zero.android.models.enums.ChannelType
import com.zero.android.network.model.ApiDirectChannel
import com.zero.android.network.model.ApiGroupChannel
import com.zero.android.network.service.ChannelService
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class ChannelRepositoryImpl
@Inject
constructor(private val channelDao: ChannelDao, private val channelService: ChannelService) :
	ChannelRepository {

	override suspend fun getDirectChannels() = flow {
		channelDao.getDirectChannels().mapNotNull { channels -> emit(channels.map { it.toModel() }) }
		channelService.getDirectChannels().firstOrNull()?.let { channels ->
			channelDao.upsert(*channels.map { it.toEntity() }.toTypedArray())
			emit(channels.map { it.toModel() })
		}
	}

	override suspend fun getGroupChannels(networkId: String) = flow {
		channelDao.getGroupChannels().mapNotNull { channels -> emit(channels.map { it.toModel() }) }
		channelService.getGroupChannels(networkId, ChannelType.GROUP).firstOrNull()?.let { channels ->
			channelDao.upsert(*channels.map { it.toEntity() }.toTypedArray())
			emit(channels.map { it.toModel() })
		}
	}

	override suspend fun getGroupChannel(id: String) = flow {
		channelDao.getGroupChannelById(id).mapNotNull { channel -> emit(channel.toModel()) }
		channelService.getChannel(id, type = ChannelType.GROUP).map {
			it as ApiGroupChannel
			channelDao.upsert(it.toEntity())
			emit(it.toModel())
		}
	}

	override suspend fun getDirectChannel(id: String) = flow {
		channelDao.getDirectChannelById(id).mapNotNull { channel -> emit(channel.toModel()) }
		channelService.getChannel(id, type = ChannelType.GROUP).map {
			it as ApiDirectChannel
			channelDao.upsert(it.toEntity())
			emit(it.toModel())
		}
	}

	override suspend fun joinChannel(channel: Channel) = channelService.joinChannel(channel)

	override suspend fun deleteChannel(channel: Channel) {
		channelDao.delete(ChannelEntity(id = channel.id, isDirectChannel = channel is DirectChannel))
		channelService.deleteChannel(channel)
	}
}
