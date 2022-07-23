package com.zero.android.feature.messages.ui.voicememo

import com.zero.android.common.ui.base.BaseViewModel
import com.zero.android.data.repository.mediaplayer.MediaPlayerRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MemoRecorderViewModel
@Inject
constructor(private val mediaPlayerRepository: MediaPlayerRepositoryImpl) : BaseViewModel() {

	private val _lastMemoPath = MutableStateFlow("")
	val lastMemoPath
		get() = _lastMemoPath.value
	val recordingState = MutableStateFlow(false)

	fun startRecording() {
		try {
			mediaPlayerRepository.startRecording()
			ioScope.launch {
				_lastMemoPath.emit(mediaPlayerRepository.recorderFilePath ?: "")
				recordingState.emit(true)
			}
		} catch (e: IOException) {
			ioScope.launch { recordingState.emit(false) }
		}
	}

	fun stopRecording() {
		mediaPlayerRepository.stopRecording()
		ioScope.launch { recordingState.emit(false) }
	}
}
