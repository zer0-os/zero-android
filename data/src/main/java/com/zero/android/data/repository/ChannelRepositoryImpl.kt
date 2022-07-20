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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChannelRepositoryImpl
@Inject
constructor(private val channelDao: ChannelDao, private val channelService: ChannelService) :
	ChannelRepository {

	override suspend fun getDirectChannels() = channelFlow {
		val listener =
			launch(Dispatchers.Unconfined) {
				channelDao
					.getDirectChannels()
					.mapNotNull { channels -> channels.map { it.toModel() } }
					.collectLatest { send(it) }
			}
		launch {
			channelService.getDirectChannels().firstOrNull()?.let { channels ->
				channelDao.upsert(*channels.map { it.toEntity() }.toTypedArray())
			}
		}
		awaitClose { listener.cancel() }
	}

	override suspend fun getGroupChannels(networkId: String) = channelFlow {
		val listener =
			launch(Dispatchers.Unconfined) {
				channelDao
					.getGroupChannels(networkId)
					.mapNotNull { channels -> channels.map { it.toModel() } }
					.collectLatest { trySend(it) }
			}
		launch {
			channelService.getGroupChannels(networkId, ChannelType.GROUP).firstOrNull()?.let { channels ->
				channelDao.upsert(*channels.map { it.toEntity() }.toTypedArray())
			}
		}
		awaitClose { listener.cancel() }
	}

	override suspend fun getGroupChannel(id: String) = channelFlow {
		val listener =
			launch(Dispatchers.Unconfined) {
				channelDao
					.getGroupChannel(id)
					.mapNotNull { channel -> channel?.toModel() }
					.collectLatest { trySend(it) }
			}
		launch {
			channelService.getChannel(id, type = ChannelType.GROUP).map {
				it as ApiGroupChannel
				channelDao.upsert(it.toEntity())
			}
		}
		awaitClose { listener.cancel() }
	}

	override suspend fun getDirectChannel(id: String) = channelFlow {
		val listener =
			launch(Dispatchers.Unconfined) {
				channelDao
					.getDirectChannel(id)
					.mapNotNull { channel -> channel?.toModel() }
					.collectLatest { trySend(it) }
			}
		launch {
			channelService.getChannel(id, type = ChannelType.GROUP).map {
				it as ApiDirectChannel
				channelDao.upsert(it.toEntity())
				trySend(it.toModel())
			}
		}
		awaitClose { listener.cancel() }
	}

	override suspend fun joinChannel(channel: Channel) = channelService.joinChannel(channel)

	override suspend fun deleteChannel(channel: Channel) {
		channelDao.delete(ChannelEntity(id = channel.id, isDirectChannel = channel is DirectChannel))
		channelService.deleteChannel(channel)
	}
}
