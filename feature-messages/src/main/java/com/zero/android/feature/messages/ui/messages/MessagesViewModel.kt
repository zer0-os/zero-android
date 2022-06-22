package com.zero.android.feature.messages.ui.messages

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.zero.android.common.ui.Result
import com.zero.android.common.ui.asResult
import com.zero.android.common.ui.base.BaseViewModel
import com.zero.android.data.repository.ChannelRepository
import com.zero.android.data.repository.ChatRepository
import com.zero.android.database.AppPreferences
import com.zero.android.feature.messages.navigation.MessagesDestination
import com.zero.android.models.Channel
import com.zero.android.models.DraftMessage
import com.zero.android.models.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MessagesViewModel
@Inject
constructor(
	savedStateHandle: SavedStateHandle,
	private val preferences: AppPreferences,
	private val chatRepository: ChatRepository,
	private val channelRepository: ChannelRepository
) : BaseViewModel() {

	private val channelId: String = checkNotNull(savedStateHandle[MessagesDestination.channelIdArg])
	val isGroupChannel: Boolean = checkNotNull(savedStateHandle[MessagesDestination.channelTypeArg])
	val loggedInUserId
		get() = runBlocking(Dispatchers.IO) { preferences.userId() }

	private val _channel = MutableStateFlow<Result<Channel>>(Result.Loading)
	private val _messages: Flow<Result<List<Message>>> = chatRepository.channelChatMessages.asResult()

	val uiState: StateFlow<ChatScreenUiState> =
		combine(_channel, _messages) { channelResult, messagesResult ->
			val chatChannelUiState =
				when (channelResult) {
					is Result.Success -> ChatChannelUiState.Success(channelResult.data)
					is Result.Loading -> ChatChannelUiState.Loading
					else -> ChatChannelUiState.Error
				}
			val messagesUiState =
				when (messagesResult) {
					is Result.Success -> MessagesUiState.Success(messagesResult.data)
					is Result.Loading -> MessagesUiState.Loading
					else -> MessagesUiState.Error
				}
			ChatScreenUiState(chatChannelUiState, messagesUiState)
		}
			.stateIn(
				scope = viewModelScope,
				started = SharingStarted.WhileSubscribed(1_000),
				initialValue = ChatScreenUiState(ChatChannelUiState.Loading, MessagesUiState.Loading)
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
			chatRepository.addChatListener(channel)
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
}
