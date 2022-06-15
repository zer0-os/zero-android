package com.zero.android.network.chat.sendbird

import com.sendbird.android.GroupChannel
import com.sendbird.android.OpenChannel
import com.zero.android.common.extensions.withSameScope
import com.zero.android.common.system.Logger
import com.zero.android.models.Channel
import com.zero.android.models.enums.ChannelType
import com.zero.android.network.chat.conversion.encodeToNetworkId
import com.zero.android.network.chat.conversion.toApi
import com.zero.android.network.chat.conversion.toParams
import com.zero.android.network.service.ChannelService
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

internal class SendBirdChannelService(private val logger: Logger) :
	SendBirdBaseService(), ChannelService {

	override suspend fun getChannels(networkId: String, type: ChannelType) = callbackFlow {
		if (type == ChannelType.OPEN) {
			val query =
				OpenChannel.createOpenChannelListQuery().apply {
					setCustomTypeFilter(networkId.encodeToNetworkId())
				}
			query.next { channels, e ->
				if (e != null) {
					logger.e("Failed to get channels", e)
					throw e
				}
				trySend(channels.map { it.toApi() })
			}
		} else if (type == ChannelType.GROUP) {
			val query =
				GroupChannel.createMyGroupChannelListQuery().apply {
					customTypeStartsWithFilter = networkId.encodeToNetworkId()
				}
			query.next { channels, e ->
				if (e != null) {
					logger.e("Failed to get channels", e)
					throw e
				}
				trySend(channels.map { it.toApi() })
			}
		}
	}

	override suspend fun createChannel(networkId: String, channel: Channel) = callbackFlow {
		if (channel is com.zero.android.models.OpenChannel) {
			OpenChannel.createChannel(channel.toParams()) { openChannel, e ->
				if (e != null) {
					logger.e("Failed to create channel", e)
					throw e
				}
				trySend(openChannel.toApi())
			}
		} else if (channel is com.zero.android.models.GroupChannel) {
			GroupChannel.createChannel(channel.toParams()) { groupChannel, e ->
				if (e != null) {
					logger.e("Failed to create channel", e)
					throw e
				}
				trySend(groupChannel.toApi())
			}
		}
	}

	override suspend fun getChannel(url: String, type: ChannelType) = callbackFlow {
		if (type == ChannelType.OPEN) {
			OpenChannel.getChannel(url) { openChannel, e ->
				if (e != null) {
					logger.e("Failed to get channel", e)
					throw e
				}
				trySend(openChannel.toApi())
			}
		} else if (type == ChannelType.GROUP) {
			GroupChannel.getChannel(url) { groupChannel, e ->
				if (e != null) {
					logger.e("Failed to get channel", e)
					throw e
				}
				trySend(groupChannel.toApi())
			}
		}
	}

	override suspend fun joinChannel(channel: Channel) =
		suspendCancellableCoroutine<Unit> { coroutine ->
			withSameScope {
				openChannel(channel.url).enter {
					if (it == null) {
						coroutine.resume(Unit)
					} else {
						logger.e("Failed to join channel", it)
						coroutine.resumeWithException(it)
					}
				}
			}
		}

	override suspend fun deleteChannel(channel: Channel) =
		suspendCancellableCoroutine<Unit> { coroutine ->
			withSameScope {
				openChannel(channel.url).delete {
					if (it == null) {
						coroutine.resume(Unit)
					} else {
						logger.e("Failed to join channel", it)
						coroutine.resumeWithException(it)
					}
				}
			}
		}
}
