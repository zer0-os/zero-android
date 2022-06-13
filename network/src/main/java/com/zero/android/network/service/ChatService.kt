package com.zero.android.network.service

import com.zero.android.network.model.ApiChannel

interface ChatService {

	suspend fun createChannel()

	suspend fun getChannel()

	suspend fun joinChannel(channel: ApiChannel)

	suspend fun sendMessage(channel: ApiChannel)

	suspend fun deleteMessage(channel: ApiChannel)
}
