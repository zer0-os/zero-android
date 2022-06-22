package com.zero.android.data.repository

import com.zero.android.data.conversion.toModel
import com.zero.android.models.Channel
import com.zero.android.models.DraftMessage
import com.zero.android.models.Message
import com.zero.android.network.chat.ChatListener
import com.zero.android.network.model.ApiChannel
import com.zero.android.network.model.ApiMessage
import com.zero.android.network.service.ChatService
import com.zero.android.network.service.MessageService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class ChatRepositoryImpl
@Inject
constructor(private val chatService: ChatService, private val messageService: MessageService) :
    ChatRepository {

    override val channelChatMessages = MutableStateFlow<List<Message>>(emptyList())

    override suspend fun getMessages(channel: Channel, timestamp: Long): Flow<List<Message>> {
        val messagesResult = chatService.getMessages(channel, timestamp).map { messages ->
            messages.map { it.toModel() }
        }
        messagesResult.collect(channelChatMessages)
        return messagesResult
    }

    override suspend fun addChatListener(channel: Channel) {
        chatService.listen(
            channel.id,
            object : ChatListener {
                override fun onMessageReceived(var1: ApiChannel, var2: ApiMessage) {
                    runBlocking(Dispatchers.IO) {
                        appendNewChatMessage(var2.toModel())
                    }
                }
            }
        )
    }

    override suspend fun getMessages(channel: Channel, id: String): Flow<List<Message>> {
        return chatService.getMessages(channel, id).map { messages -> messages.map { it.toModel() } }
    }

    override suspend fun send(channel: Channel, message: DraftMessage): Flow<Message> {
        val newMessage = chatService.send(channel, message).map { it.toModel() }
        newMessage.collectLatest { appendNewChatMessage(it) }
        return newMessage
    }

    override suspend fun reply(channel: Channel, id: String, message: DraftMessage): Flow<Message> {
        return chatService.reply(channel, id, message).map { it.toModel() }
    }

    override suspend fun updateMessage(id: String, channelId: String, text: String) {
        messageService.updateMessage(id, channelId, text)
    }

    override suspend fun deleteMessage(id: String, channelId: String) {
        return messageService.deleteMessage(id, channelId)
    }

    private suspend fun appendNewChatMessage(message: Message) {
        val messages = channelChatMessages.firstOrNull()?.toMutableList() ?: mutableListOf()
        messages.add(0, message)
        channelChatMessages.tryEmit(messages)
    }
}
