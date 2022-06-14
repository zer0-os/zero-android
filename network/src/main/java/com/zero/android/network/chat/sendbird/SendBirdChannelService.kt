package com.zero.android.network.chat.sendbird

import com.sendbird.android.OpenChannel
import com.zero.android.common.extensions.emitInScope
import com.zero.android.common.extensions.withSameScope
import com.zero.android.common.system.Logger
import com.zero.android.models.Channel
import com.zero.android.models.enums.ChannelType
import com.zero.android.network.chat.conversion.toApi
import com.zero.android.network.service.ChannelService
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

internal class SendBirdChannelService(private val logger: Logger) :
	SendBirdBaseService(), ChannelService {

	override suspend fun createChannel(networkId: String) = flow {
		OpenChannel.createChannel { openChannel, e ->
			if (e != null) {
				logger.e("Failed to create channel", e)
				throw e
			}
			emitInScope(openChannel.toApi())
		}
	}

	override suspend fun getChannel(url: String, type: ChannelType) = flow {
		OpenChannel.getChannel(url) { openChannel, e ->
			if (e != null) {
				logger.e("Failed to get channel", e)
				throw e
			}
			emitInScope(openChannel.toApi())
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
