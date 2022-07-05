package com.zero.android.feature.messages.ui.voicememo.mediaPlayer

import com.zero.android.common.extensions.isValidUrl
import com.zero.android.common.ui.base.BaseViewModel
import com.zero.android.models.Message
import com.zero.android.models.enums.MessageType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaSourceViewModel @Inject constructor(
    private val mediaPlayerRepository: MediaPlayerRepository
) : BaseViewModel() {

    private val voiceMemoMediaSources = mutableMapOf<String, MediaSourceProvider>()
    private var lastMediaId: String? = null

    fun configure(messages: List<Message>) {
        ioScope.launch {
            messages.filter { it.type == MessageType.AUDIO }.forEach {
                val mediaSource = MediaSourceProvider(it.fileName, mediaPlayerRepository)
                voiceMemoMediaSources[it.id] = mediaSource
            }
        }
    }

    fun getMediaSource(message: Message) = voiceMemoMediaSources.getOrPut(message.id) {
        MediaSourceProvider(message.fileName, mediaPlayerRepository)
    }

    fun dispose() {
        mediaPlayerRepository.mediaPlayer.stop()
        ioScope.launch {
            voiceMemoMediaSources.forEach { it.value.reset() }
            voiceMemoMediaSources.clear()
        }
    }

    fun downloadAndPrepareMedia(message: Message) {
        val mediaUrl = message.fileUrl
        if (mediaUrl?.isValidUrl == true) {
            getMediaSource(message).downloadFileAndPrepare(mediaUrl)
        }
    }

    fun play(message: Message) {
        voiceMemoMediaSources.values.forEach { it.reset() }
        getMediaSource(message).play()
        lastMediaId = message.id
    }

    fun stop() {
        lastMediaId?.let {
            voiceMemoMediaSources[lastMediaId]?.stop()
        }
    }

    fun seekMediaTo(message: Message, value: Float) {
        getMediaSource(message).seekTo(value)
    }

}
