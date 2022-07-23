package com.zero.android.data.repository.mediaplayer

import android.media.MediaPlayer
import android.net.Uri
import java.io.File
import java.io.IOException

interface MediaPlayerRepository {

    val baseFilePath: String

    val mediaPlayer: MediaPlayer

    @Throws(IOException::class)
    fun startRecording()

    @Throws(IOException::class)
    fun stopRecording()

    fun prepareMediaPlayer(uri: Uri, onPlayBackComplete: () -> Unit)

    fun getFileDuration(file: File): String?
}
