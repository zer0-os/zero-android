package com.zero.android.feature.messages.ui.voicememo

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.net.Uri
import com.zero.android.common.extensions.downloadFile
import com.zero.android.common.extensions.toUrl
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.File
import java.io.IOException

class MediaSourceProvider(
    val context: Context,
    private val fileName: String?
) {
    private val filePath by lazy {
        "${context.externalCacheDir?.absolutePath ?: ""}/Memos/${fileName}"
    }
    private val currentFile by lazy { File(filePath) }

    private val ioJob = SupervisorJob()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }
    private val ioScope = CoroutineScope(Dispatchers.IO + ioJob + exceptionHandler)

    val currentFileState = MutableStateFlow(VoiceMessageState.DOWNLOAD)
    val mediaFileDuration = MutableStateFlow(0)

    init {
        if (currentFile.exists()) {
            prepareMediaPlayer()
        } else {
            ioScope.launch { currentFileState.emit(VoiceMessageState.DOWNLOAD) }
        }
    }

    private fun prepareMediaPlayer() {
        ioScope.launch {
            currentFileState.emit(VoiceMessageState.STOPPED)
            evaluateFileDuration()
        }
    }

    private suspend fun evaluateFileDuration() {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(context, Uri.fromFile(currentFile))
        val time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        mediaFileDuration.emit(time?.toInt() ?: 0)
        retriever.release()
    }

    fun downloadFileAndPrepare(fileUrl: String) {
        ioScope.launch(Dispatchers.IO) {
            currentFileState.emit(VoiceMessageState.DOWNLOADING)
            fileUrl.toUrl.downloadFile(currentFile.absolutePath) { progress, total ->
                if (progress == total) {
                    prepareMediaPlayer()
                }
            }
        }
    }

    fun play() {
        ioScope.launch { currentFileState.emit(VoiceMessageState.PLAYING) }
        MemoPlayer.prepareMediaPlayer(context, Uri.fromFile(currentFile)) {
            ioScope.launch { currentFileState.emit(VoiceMessageState.STOPPED) }
        }.start()
    }

    fun pause() {
        ioScope.launch { currentFileState.emit(VoiceMessageState.STOPPED) }
        MemoPlayer.pause()
    }
}

private object MemoPlayer {
    private val mediaPlayer = MediaPlayer().apply {
        setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build()
        )
    }

    fun prepareMediaPlayer(context: Context, uri: Uri, onPlayBackComplete: () -> Unit) =
        mediaPlayer.apply {
            try {
                stop()
                reset()
                setDataSource(context, uri)
                setOnCompletionListener { onPlayBackComplete.invoke() }
                prepare()
            } catch (e: IOException) {

            }
        }

    fun pause() {
        mediaPlayer.pause()
    }

}
