package com.zero.android.data.manager

import com.zero.android.datastore.AppPreferences
import com.zero.android.datastore.ChatPreferences
import com.zero.android.network.chat.ChatProvider
import javax.inject.Inject

class ConnectionManagerImpl
@Inject
constructor(
	private val chatProvider: ChatProvider,
	private val preferences: AppPreferences,
	private val chatPreferences: ChatPreferences
) : ConnectionManager {

	override suspend fun connect() =
		chatProvider.connect(preferences.userId(), chatPreferences.chatToken())

	override suspend fun disconnect() = chatProvider.disconnect()
}
