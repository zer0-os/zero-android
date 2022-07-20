package com.zero.android.data.repository.chat

import com.zero.android.data.conversion.toEntity
import com.zero.android.database.dao.MessageDao
import com.zero.android.network.chat.ChatListener
import com.zero.android.network.model.ApiChannel
import com.zero.android.network.model.ApiMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

internal class MessageListener @Inject constructor(private val messageDao: MessageDao) :
	ChatListener {

	override fun onMessageReceived(channel: ApiChannel, message: ApiMessage) {
		runBlocking(Dispatchers.IO) { messageDao.upsert(message.toEntity()) }
	}

	override fun onMessageUpdated(channel: ApiChannel, message: ApiMessage) {
		runBlocking(Dispatchers.IO) { messageDao.upsert(message.toEntity()) }
	}

	override fun onMessageDeleted(channel: ApiChannel, msgId: String) {
		runBlocking(Dispatchers.IO) { messageDao.delete(msgId) }
	}
}
