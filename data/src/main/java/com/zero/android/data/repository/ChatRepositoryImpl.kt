package com.zero.android.data.repository

import com.zero.android.network.service.ChatService
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(private val chatService: ChatService) : ChatRepository
