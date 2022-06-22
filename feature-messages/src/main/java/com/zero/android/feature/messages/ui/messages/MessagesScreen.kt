package com.zero.android.feature.messages.ui.messages

import android.annotation.SuppressLint
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.zero.android.common.R
import com.zero.android.common.ui.Result
import com.zero.android.models.*
import com.zero.android.ui.components.AppBar
import com.zero.android.ui.components.Background
import com.zero.android.ui.components.SmallCircularImage
import com.zero.android.ui.extensions.Preview
import com.zero.android.ui.theme.AppTheme

@Composable
fun MessagesRoute(viewModel: MessagesViewModel = hiltViewModel()) {
	val channel: Result<Channel> by viewModel.channel.collectAsState()
	val messages: Result<List<Message>> by viewModel.messages.collectAsState()
	LaunchedEffect(Unit) { viewModel.loadChannel() }
	MessagesScreen(viewModel, channel, messages)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagesScreen(
	viewModel: MessagesViewModel,
	channelResult: Result<Channel>,
	messages: Result<List<Message>>
) {
	val navController = rememberNavController()
	val loggedInUserId = viewModel.loggedInUserId
	val isGroupChannel = viewModel.isGroupChannel
	val messagesUiState = MessagesUiState(loggedInUserId, channelResult, messages)

	messagesUiState.channel?.let { channel ->
		val topBar: @Composable () -> Unit = {
			AppBar(
				scrollBehavior = null,
				navIcon = {
					IconButton(onClick = { navController.navigateUp() }) {
						Icon(
							imageVector = Icons.Filled.ArrowBack,
							contentDescription = "cd_back",
							tint = AppTheme.colors.glow
						)
					}
				},
				title = { ConversationAppBarTitle(loggedInUserId, channel, isGroupChannel) },
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
				MessagesContent(isDirectChannelMessage = !isGroupChannel, uiState = messagesUiState) {
					viewModel.sendMessage(it)
				}
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
			SmallCircularImage(placeHolder = R.drawable.ic_circular_image_placeholder)
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
