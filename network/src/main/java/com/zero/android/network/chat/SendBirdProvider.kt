package com.zero.android.network.chat

import android.content.Context
import com.sendbird.android.SendBird
import com.sendbird.android.SendBirdException
import com.sendbird.android.handlers.InitResultHandler
import com.zero.android.common.system.Logger
import com.zero.android.network.BuildConfig
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

internal class SendBirdProvider(private val logger: Logger) : ChatProvider {

	override fun initialize(context: Context) {
		SendBird.init(
			BuildConfig.SEND_BIRD_ID,
			context,
			true,
			object : InitResultHandler {
				override fun onMigrationStarted() {
					logger.i("Called when there's an update in Sendbird server.")
				}

				override fun onInitFailed(e: SendBirdException) {
					logger.i(
						"Called when initialize failed. SDK will still operate properly as if useLocalCaching is set to false."
					)
				}

				override fun onInitSucceed() {
					logger.i("Called when initialization is completed.")
				}
			}
		)
	}

	override suspend fun connect(userId: String) =
		suspendCoroutine<Exception?> {
			SendBird.connect(userId) { user, e ->
				if (user != null) {
					if (e != null) {
						// Proceed in offline mode with the data stored in the local database.
						// Later, connection will be made automatically
						// and can be notified through the ConnectionHandler.onReconnectSucceeded().
						it.resume(null)
					} else {
						// Proceed in online mode.
						it.resume(null)
					}
				} else {
					// Handle error.
					it.resumeWithException(e)
				}
			}
		}
}
