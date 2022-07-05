package com.zero.android.feature.messages.ui.voicememo.mediaPlayer

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediaPlayerRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    val baseFilePath by lazy { "${context.externalCacheDir?.absolutePath ?: ""}/Memos" }

    init {
        File(baseFilePath).apply {
            if (!this.exists()) {
                this.mkdirs()
            }
        }
    }

    private var _recorder: MediaRecorder? = null
    var recorderFilePath: String? = null

    val mediaPlayer = MediaPlayer().apply {
        setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build()
        )
    }

    @Throws(IOException::class)
    fun startRecording() {
        recorderFilePath = "$baseFilePath/Memo-${System.currentTimeMillis().div(1000)}"
        recorderFilePath?.let {
            _recorder = MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setOutputFile(it)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setAudioEncodingBitRate(16 * 44100)
                setAudioSamplingRate(44100)

                prepare()
                start()
            }
        }
    }

    @Throws(IOException::class)
    fun stopRecording() {
        _recorder?.apply {
            stop()
            reset()
            release()
        }
        _recorder = null
    }

    fun prepareMediaPlayer(uri: Uri, onPlayBackComplete: () -> Unit) {
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
    }

    fun getFileDuration(file: File): String? {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(context, Uri.fromFile(file))
        val time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        retriever.release()
        return time
    }
}
