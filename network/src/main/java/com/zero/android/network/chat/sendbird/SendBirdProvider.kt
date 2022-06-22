package com.zero.android.network.chat.sendbird

import android.content.Context
import com.sendbird.android.SendBird
import com.sendbird.android.SendBirdException
import com.sendbird.android.handlers.InitResultHandler
import com.zero.android.common.system.Logger
import com.zero.android.network.BuildConfig
import com.zero.android.network.chat.ChatProvider
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
                    logger.i("There's an update in Sendbird server.")
                }

                override fun onInitFailed(e: SendBirdException) {
                    logger.e(
                        "SendBird initialize failed. SDK will still operate properly as if useLocalCaching is set to false.",
                        e
                    )
                }

                override fun onInitSucceed() {
                    logger.i("Initialization is completed.")
                }
            }
        )
    }

    override suspend fun connect(userId: String, accessToken: String?) =
        suspendCoroutine<Unit> {
            logger.i("Connecting to SendBird")
            SendBird.connect(userId, accessToken) { user, e ->
                if (user != null) {
                    if (e != null) {
                        // Proceed in offline mode with the data stored in the local database.
                        // Later, connection will be made automatically
                        // and can be notified through the ConnectionHandler.onReconnectSucceeded().
                        it.resume(Unit)
                    } else {
                        // Proceed in online mode.
                        it.resume(Unit)
                    }
                } else {
                    // Handle error.
                    logger.w("Failed to connect to SendBird")
                    it.resumeWithException(e)
                }
            }
        }

    override suspend fun disconnect() =
        suspendCoroutine<Unit> { SendBird.disconnect { it.resume(Unit) } }
}
