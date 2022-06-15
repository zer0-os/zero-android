package com.zero.android.feature.messages.ui.messages

import androidx.compose.runtime.mutableStateListOf
import com.zero.android.models.Message

class MessagesUiState(
    val channelName: String,
    val channelMembers: Int,
    initialMessages: List<Message>
) {
    private val _messages: MutableList<Message> =
        mutableStateListOf(*initialMessages.toTypedArray())
    val messages: List<Message> = _messages

    fun addMessage(msg: Message) {
        // Add to the beginning of the list
        _messages.add(0, msg)
    }
}
