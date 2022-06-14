package com.zero.android.network.chat.sendbird

import com.sendbird.android.BaseChannel
import com.sendbird.android.GroupChannel
import com.sendbird.android.OpenChannel
import com.zero.android.models.Channel
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

internal abstract class SendBirdBaseService {

	protected suspend fun getChannel(channel: Channel): BaseChannel {
		if (channel is com.zero.android.models.OpenChannel) return openChannel(channel.url)
		else if (channel is com.zero.android.models.GroupChannel) return groupChannel(channel.url)
		throw IllegalStateException("not handled")
	}

	protected suspend fun openChannel(url: String): OpenChannel = suspendCancellableCoroutine {
		OpenChannel.getChannel(url) { openChannel, e ->
			if (e == null) it.resume(openChannel) else it.resumeWithException(e)
		}
	}

	protected suspend fun groupChannel(url: String): GroupChannel = suspendCancellableCoroutine {
		GroupChannel.getChannel(url) { openChannel, e ->
			if (e == null) it.resume(openChannel) else it.resumeWithException(e)
		}
	}
}
