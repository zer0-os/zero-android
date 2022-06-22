package com.zero.android.data.manager

import com.zero.android.database.AppPreferences
import com.zero.android.network.chat.ChatProvider
import javax.inject.Inject

class ConnectionManagerImpl
@Inject
constructor(private val chatProvider: ChatProvider, private val preferences: AppPreferences) :
	ConnectionManager {

	override suspend fun connect() =
		chatProvider.connect(preferences.userId(), preferences.chatToken())

	override suspend fun disconnect() = chatProvider.disconnect()
}
