package com.zero.android.feature.messages.helper

import com.zero.android.models.Message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

object MessageActionStateHandler {
	private val ioScope by lazy { CoroutineScope(Dispatchers.IO) }

	private val _editableMessage: MutableStateFlow<Message?> = MutableStateFlow(null)
	val editableMessage: StateFlow<Message?> = _editableMessage
	private val _selectedMessage: MutableStateFlow<Message?> = MutableStateFlow(null)
	val selectedMessage: StateFlow<Message?> = _selectedMessage
	private val _replyToMessage: MutableStateFlow<Message?> = MutableStateFlow(null)
	val replyToMessage: StateFlow<Message?> = _replyToMessage

	val isActionModeStarted: Boolean
		get() = _selectedMessage.value != null

	fun setSelectedMessage(msg: Message) {
		ioScope.launch { _selectedMessage.emit(msg) }
	}

	fun editTextMessage() {
		ioScope.launch {
			if (_selectedMessage.value != null) {
				_editableMessage.emit(_selectedMessage.value)
			}
			_selectedMessage.emit(null)
			_replyToMessage.emit(null)
		}
	}

	fun replyToMessage() {
		ioScope.launch {
			if (_selectedMessage.value != null) {
				_replyToMessage.emit(_selectedMessage.value)
			}
			_selectedMessage.emit(null)
			_editableMessage.emit(null)
		}
	}

	fun closeActionMode() {
		ioScope.launch {
			_selectedMessage.emit(null)
			_editableMessage.emit(null)
			_replyToMessage.emit(null)
		}
	}

	fun reset() {
		closeActionMode()
	}
}
