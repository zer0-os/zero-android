package com.zero.android.feature.messages.ui.messages

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toFile
import androidx.hilt.navigation.compose.hiltViewModel
import com.zero.android.common.R
import com.zero.android.common.extensions.toFile
import com.zero.android.models.Channel
import com.zero.android.models.GroupChannel
import com.zero.android.models.getTitle
import com.zero.android.ui.components.AppBar
import com.zero.android.ui.components.Background
import com.zero.android.ui.components.NameInitialsView
import com.zero.android.ui.components.SmallCircularImage
import com.zero.android.ui.extensions.Preview
import com.zero.android.ui.theme.AppTheme

@Composable
fun MessagesRoute(onBackClick: () -> Unit, viewModel: MessagesViewModel = hiltViewModel()) {
	val chatUiState: ChatScreenUiState by viewModel.uiState.collectAsState()
	val userChannelInfo = viewModel.loggedInUserId to viewModel.isGroupChannel
	val context = LocalContext.current
	LaunchedEffect(Unit) { viewModel.loadChannel() }

	val imageSelectorLauncher =
		rememberLauncherForActivityResult(
			contract = ActivityResultContracts.StartActivityForResult(),
			onResult = {
				val resultCode = it.resultCode
				val data = it.data
				if (resultCode == Activity.RESULT_OK) {
					// Image Uri will not be null for RESULT_OK
					val fileUri = data?.data!!
					val file =
						try {
							fileUri.toFile()
						} catch (e: Exception) {
							fileUri.toFile(context)
						}
					viewModel.sendMessage(
						chatUiState.newFileMessage(file = file, currentUserId = userChannelInfo.first)
					)
				}
			}
		)
	MessagesScreen(
		onBackClick,
		imageSelectorLauncher,
		userChannelInfo,
		chatUiState.channelUiState,
		chatUiState.messagesUiState
	) { newMessage ->
		viewModel.sendMessage(
			chatUiState.newTextMessage(msg = newMessage, currentUserId = userChannelInfo.first)
		)
	}
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagesScreen(
	onBackClick: () -> Unit,
	imageSelectorLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>,
	userChannelInfo: Pair<String, Boolean>,
	chatChannelUiState: ChatChannelUiState,
	messagesUiState: MessagesUiState,
	onNewMessage: (String) -> Unit
) {
	if (chatChannelUiState is ChatChannelUiState.Success) {
		val topBar: @Composable () -> Unit = {
			AppBar(
				scrollBehavior = null,
				navIcon = {
					IconButton(onClick = onBackClick) {
						Icon(
							imageVector = Icons.Filled.ArrowBack,
							contentDescription = "cd_back",
							tint = AppTheme.colors.glow
						)
					}
				},
				title = {
					ConversationAppBarTitle(
						userChannelInfo.first,
						chatChannelUiState.channel,
						userChannelInfo.second
					)
				},
				actions = {
					IconButton(onClick = {}) {
						Icon(
							painter = painterResource(R.drawable.ic_search),
							contentDescription = "cd_search_message"
						)
					}
					IconButton(onClick = {}) {
						Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "cd_more_options")
					}
				}
			)
		}
		Scaffold(topBar = { topBar() }) {
			Background {
				MessagesContent(
					userChannelInfo = userChannelInfo,
					uiState = messagesUiState,
					imageSelectorLauncher = imageSelectorLauncher
				) { onNewMessage(it) }
			}
		}
	}
}

@Preview @Composable
fun MessagesScreenPreview() = Preview {}

@Composable
fun ConversationAppBarTitle(loggedInUserId: String, channel: Channel, isGroupChannel: Boolean) {
	Row {
		IconButton(modifier = Modifier.align(Alignment.CenterVertically), onClick = {}) {
			if (isGroupChannel) {
				NameInitialsView(userName = channel.getTitle(loggedInUserId))
			} else {
				SmallCircularImage(
					imageUrl = channel.members.firstOrNull()?.profileImage,
					placeHolder = R.drawable.ic_user_profile_placeholder
				)
			}
		}
		Text(
			channel.getTitle(loggedInUserId).lowercase(),
			modifier = Modifier.align(Alignment.CenterVertically)
		)
		Spacer(modifier = Modifier.padding(6.dp))
		if (isGroupChannel) {
			if ((channel as GroupChannel).hasTelegramChannel) {
				Image(
					painter = painterResource(R.drawable.ic_vector),
					contentDescription = "",
					modifier = Modifier.wrapContentSize().align(Alignment.CenterVertically),
					contentScale = ContentScale.Fit
				)
				Spacer(modifier = Modifier.padding(6.dp))
			}
			if (channel.hasDiscordChannel) {
				Image(
					painter = painterResource(R.drawable.ic_discord),
					contentDescription = "",
					modifier = Modifier.wrapContentSize().align(Alignment.CenterVertically),
					contentScale = ContentScale.Fit
				)
			}
		}
	}
}
