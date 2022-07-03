package com.zero.android.feature.messages.ui.voicememo

import android.media.MediaRecorder
import com.zero.android.common.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MemoRecorderViewModel @Inject constructor() : BaseViewModel() {

	private var _recorder: MediaRecorder? = null
	private var _folderPath: String = ""
	private val _lastMemoPath = MutableStateFlow("")

	val lastMemoPath
		get() = _lastMemoPath.value
	val recordingState = MutableStateFlow(false)

	fun configure(path: String? = "") {
		_folderPath = "$path/Memos"
		File(_folderPath).apply {
			if (!this.exists()) {
				this.mkdirs()
			}
		}
	}

	fun startRecording() {
		val filePath = "$_folderPath/Memo-${System.currentTimeMillis().div(1000)}"
		_recorder =
			MediaRecorder().apply {
				setAudioSource(MediaRecorder.AudioSource.MIC)
				setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
				setOutputFile(filePath)
				setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
				setAudioEncodingBitRate(16 * 44100)
				setAudioSamplingRate(44100)
			}
		try {
			_recorder?.prepare()
			_recorder?.start()
			ioScope.launch {
				_lastMemoPath.emit(filePath)
				recordingState.emit(true)
			}
		} catch (e: IOException) {
			ioScope.launch { recordingState.emit(false) }
		}
	}

	fun stopRecording() {
		_recorder?.apply {
			stop()
			reset()
			release()
		}
		_recorder = null
		ioScope.launch { recordingState.emit(false) }
	}
}
