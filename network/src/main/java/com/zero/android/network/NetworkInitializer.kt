package com.zero.android.network

import android.content.Context
import com.zero.android.network.chat.ChatProvider
import dagger.hilt.android.qualifiers.ApplicationContext

class NetworkInitializer(private val chatProvider: ChatProvider) {

	fun initialize(@ApplicationContext context: Context) {
		chatProvider.initialize(context)
	}
}
