package com.zero.android.feature.messages.ui.messages

import androidx.lifecycle.SavedStateHandle
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
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
	val channel = MutableStateFlow<Result<Channel>>(Result.Loading)
	val messages = MutableStateFlow<Result<List<Message>>>(Result.Loading)

	fun loadChannel() {
		ioScope.launch {
			val request =
				if (isGroupChannel) {
					channelRepository.getGroupChannel(channelId)
				} else {
					channelRepository.getDirectChannel(channelId)
				}
			request.asResult().collectLatest {
				channel.emit(it)
				if (it is Result.Success) {
					loadChannelMessage(it.data)
				}
			}
		}
	}

	private fun loadChannelMessage(channel: Channel) {
		ioScope.launch {
			chatRepository.getMessages(channel).asResult().collectLatest { messages.emit(it) }
		}
	}

	fun sendMessage(message: DraftMessage) {
		ioScope.launch {
			(channel.firstOrNull() as? Result.Success)?.data?.let { channel ->
				chatRepository.send(channel, message).asResult().collectLatest { newMessageResult ->
					if (newMessageResult is Result.Success) {
						loadChannelMessage(channel)
					}
				}
			}
		}
	}
}
