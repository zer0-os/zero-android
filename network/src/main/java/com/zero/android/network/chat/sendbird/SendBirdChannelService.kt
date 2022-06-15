package com.zero.android.network.chat.sendbird

import com.sendbird.android.GroupChannel
import com.sendbird.android.OpenChannel
import com.zero.android.common.extensions.withSameScope
import com.zero.android.common.system.Logger
import com.zero.android.models.Channel
import com.zero.android.models.DirectChannel
import com.zero.android.models.enums.ChannelType
import com.zero.android.network.chat.conversion.encodeToNetworkId
import com.zero.android.network.chat.conversion.isGroupChannel
import com.zero.android.network.chat.conversion.isOpenChannel
import com.zero.android.network.chat.conversion.toApi
import com.zero.android.network.chat.conversion.toDirectApi
import com.zero.android.network.chat.conversion.toGroupApi
import com.zero.android.network.chat.conversion.toOpenParams
import com.zero.android.network.chat.conversion.toParams
import com.zero.android.network.service.ChannelService
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

internal class SendBirdChannelService(private val logger: Logger) :
	SendBirdBaseService(), ChannelService {

	override suspend fun getGroupChannels(networkId: String, type: ChannelType) = callbackFlow {
		if (type == ChannelType.OPEN) {
			val query =
				OpenChannel.createOpenChannelListQuery().apply {
					setCustomTypeFilter(networkId.encodeToNetworkId())
				}
			query.next { channels, e ->
				if (e != null) {
					logger.e("Failed to get open channels", e)
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
					logger.e("Failed to get group channels", e)
					throw e
				}
				trySend(channels.map { it.toGroupApi() })
			}
		}
	}

	override suspend fun getDirectChannels() = callbackFlow {
		GroupChannel.createMyGroupChannelListQuery().next { channels, e ->
			if (e != null) {
				logger.e("Failed to get direct channels", e)
				throw e
			}
			trySend(channels.map { it.toDirectApi() })
		}
	}

	override suspend fun createChannel(networkId: String, channel: Channel) = callbackFlow {
		if (channel.isGroupChannel()) {
			val params =
				when (channel) {
					is DirectChannel -> channel.toParams()
					is com.zero.android.models.GroupChannel -> channel.toParams()
					else -> throw IllegalStateException()
				}

			GroupChannel.createChannel(params) { groupChannel, e ->
				if (e != null) {
					logger.e("Failed to create channel", e)
					throw e
				}
				trySend(groupChannel.toApi())
			}
		} else if (channel.isOpenChannel()) {
			OpenChannel.createChannel((channel as com.zero.android.models.GroupChannel).toOpenParams()) {
					openChannel,
					e ->
				if (e != null) {
					logger.e("Failed to create channel", e)
					throw e
				}
				trySend(openChannel.toApi())
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
				if (channel.isGroupChannel()) {
					val accessCode =
						when (channel) {
							is DirectChannel -> channel.accessCode
							is com.zero.android.models.GroupChannel -> channel.accessCode
							else -> throw IllegalStateException()
						}
					groupChannel(channel.id).join(accessCode) {
						if (it == null) {
							coroutine.resume(Unit)
						} else {
							logger.e("Failed to join channel", it)
							coroutine.resumeWithException(it)
						}
					}
				} else if (channel.isOpenChannel()) {
					openChannel(channel.id).enter {
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

	override suspend fun deleteChannel(channel: Channel) =
		suspendCancellableCoroutine<Unit> { coroutine ->
			withSameScope {
				if (channel.isGroupChannel()) {
					groupChannel(channel.id).delete {
						if (it == null) {
							coroutine.resume(Unit)
						} else {
							logger.e("Failed to join channel", it)
							coroutine.resumeWithException(it)
						}
					}
				} else if (channel.isOpenChannel()) {
					openChannel(channel.id).delete {
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
}
