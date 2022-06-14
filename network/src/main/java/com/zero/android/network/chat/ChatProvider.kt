package com.zero.android.network.chat

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext

interface ChatProvider {

	fun initialize(@ApplicationContext context: Context)

	suspend fun connect(userId: String)
}
