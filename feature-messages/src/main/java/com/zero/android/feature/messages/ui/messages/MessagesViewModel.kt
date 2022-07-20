package com.zero.android.feature.messages.ui.messages

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.zero.android.common.ui.Result
import com.zero.android.common.ui.asResult
import com.zero.android.common.ui.base.BaseViewModel
import com.zero.android.data.delegates.Preferences
import com.zero.android.data.repository.ChannelRepository
import com.zero.android.data.repository.ChatRepository
import com.zero.android.feature.messages.navigation.MessagesDestination
import com.zero.android.models.Channel
import com.zero.android.models.DraftMessage
import com.zero.android.models.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MessagesViewModel
@Inject
constructor(
	savedStateHandle: SavedStateHandle,
	private val preferences: Preferences,
	private val chatRepository: ChatRepository,
	private val channelRepository: ChannelRepository
) : BaseViewModel() {

	private val channelId: String = checkNotNull(savedStateHandle[MessagesDestination.channelIdArg])
	val isGroupChannel: Boolean = checkNotNull(savedStateHandle[MessagesDestination.channelTypeArg])
	val loggedInUserId
		get() = runBlocking(Dispatchers.IO) { preferences.userId() }

	private val _channel = MutableStateFlow<Result<Channel>>(Result.Loading)

	val messages: Flow<PagingData<Message>> = chatRepository.channelChatMessages
	private val _messagesResult = messages.asResult()

	val uiState: StateFlow<ChatScreenUiState> =
		combine(_channel, _messagesResult) { channelResult, messagesResult ->
			ChatScreenUiState(channelUiState = channelResult, messagesUiState = messagesResult)
		}
			.stateIn(
				scope = viewModelScope,
				started = SharingStarted.WhileSubscribed(1_000),
				initialValue =
				ChatScreenUiState(
					channelUiState = Result.Loading,
					messagesUiState = Result.Loading
				)
			)

	fun loadChannel() {
		ioScope.launch {
			val request =
				if (isGroupChannel) {
					channelRepository.getGroupChannel(channelId)
				} else {
					channelRepository.getDirectChannel(channelId)
				}
			request.asResult().collectLatest {
				_channel.emit(it)
				if (it is Result.Success) configureChat(it.data)
			}
		}
	}

	private fun configureChat(channel: Channel) {
		ioScope.launch {
			chatRepository.addListener(channel.id)
			chatRepository.getMessages(channel)
		}
	}

	fun sendMessage(message: DraftMessage) {
		ioScope.launch {
			(_channel.firstOrNull() as? Result.Success)?.data?.let { channel ->
				chatRepository.send(channel, message)
			}
		}
	}

	override fun onCleared() {
		runBlocking { chatRepository.removeListener(channelId) }
		super.onCleared()
	}
}
