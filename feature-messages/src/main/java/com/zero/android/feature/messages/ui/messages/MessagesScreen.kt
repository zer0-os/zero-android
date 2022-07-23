package com.zero.android.feature.messages.ui.messages

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toFile
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import com.zero.android.common.R
import com.zero.android.common.extensions.getActivity
import com.zero.android.common.extensions.toFile
import com.zero.android.feature.messages.helper.MessageActionStateHandler
import com.zero.android.feature.messages.ui.components.ChatScreenAppBarTitle
import com.zero.android.feature.messages.ui.components.ReplyMessage
import com.zero.android.feature.messages.ui.components.UserInputPanel
import com.zero.android.feature.messages.ui.voicememo.MemoRecorderViewModel
import com.zero.android.feature.messages.ui.voicememo.RecordMemoView
import com.zero.android.feature.messages.util.MessageUtil
import com.zero.android.models.Message
import com.zero.android.models.enums.MessageType
import com.zero.android.ui.components.AppBar
import com.zero.android.ui.components.Background
import com.zero.android.ui.components.BottomBarDivider
import com.zero.android.ui.extensions.Preview
import com.zero.android.ui.theme.AppTheme
import com.zero.android.ui.util.BackHandler
import java.io.File

@Composable
fun MessagesRoute(
	onBackClick: () -> Unit,
	viewModel: MessagesViewModel = hiltViewModel(),
	recordMemoViewModel: MemoRecorderViewModel = hiltViewModel()
) {
	val chatUiState: ChatScreenUiState by viewModel.uiState.collectAsState()
	val recordingState: Boolean by recordMemoViewModel.recordingState.collectAsState()
	val userChannelInfo = viewModel.loggedInUserId to viewModel.isGroupChannel
	val context = LocalContext.current

	LaunchedEffect(Unit) { viewModel.loadChannel() }
	BackHandler {
		if (MessageActionStateHandler.isActionModeStarted) {
			MessageActionStateHandler.closeActionMode()
		} else {
			onBackClick()
		}
	}

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
					val message =
						MessageUtil.newFileMessage(
							file = file,
							authorId = userChannelInfo.first,
							type = MessageType.IMAGE
						)
					val replyToMessage = MessageActionStateHandler.replyToMessage.value
					if (replyToMessage != null) {
						viewModel.replyToMessage(replyToMessage.id, message)
						MessageActionStateHandler.reset()
					} else {
						viewModel.sendMessage(message)
					}
				}
			}
		)
	val recordMemoPermissionLauncher =
		rememberLauncherForActivityResult(
			contract = ActivityResultContracts.RequestPermission(),
			onResult = { granted -> if (granted) recordMemoViewModel.startRecording() }
		)

	MessagesScreen(
		onBackClick,
		userChannelInfo,
		chatUiState.channelUiState,
		chatUiState.messagesUiState,
		recordingState,
		onNewMessage = { newMessage ->
			viewModel.sendMessage(
				MessageUtil.newTextMessage(msg = newMessage, authorId = userChannelInfo.first)
			)
		},
		onPickImage = { imageSelectorLauncher.launch(it) },
		onRecordMemo = {
			val isRecording = recordMemoViewModel.recordingState.value
			if (isRecording) {
				recordMemoViewModel.stopRecording()
			} else {
				recordMemoPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
			}
		},
		onSendMemo = {
			recordMemoViewModel.stopRecording()
			val file = File(recordMemoViewModel.lastMemoPath)
			if (file.exists()) {
				val message =
					MessageUtil.newFileMessage(
						file = file,
						authorId = userChannelInfo.first,
						type = MessageType.AUDIO
					)
				val replyToMessage = MessageActionStateHandler.replyToMessage.value
				if (replyToMessage != null) {
					viewModel.replyToMessage(replyToMessage.id, message)
					MessageActionStateHandler.reset()
				} else {
					viewModel.sendMessage(message)
				}
			}
		},
		onEditMessage = { viewModel.updateMessage(it) },
		onDeleteMessage = { viewModel.deleteMessage(it) },
		onReplyToMessage = { messageId, reply ->
			val replyMessage = MessageUtil.newTextMessage(reply, userChannelInfo.first)
			viewModel.replyToMessage(messageId, replyMessage)
		}
	)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagesScreen(
	onBackClick: () -> Unit,
	userChannelInfo: Pair<String, Boolean>,
	chatChannelUiState: ChatChannelUiState,
	messagesUiState: MessagesUiState,
	isMemoRecording: Boolean,
	onNewMessage: (String) -> Unit,
	onPickImage: (Intent) -> Unit,
	onRecordMemo: () -> Unit,
	onSendMemo: () -> Unit,
	onEditMessage: (Message) -> Unit,
	onDeleteMessage: (Message) -> Unit,
	onReplyToMessage: (String, String) -> Unit
) {
	val context = LocalContext.current
	val actionMessage by MessageActionStateHandler.selectedMessage.collectAsState()
	val editableMessage by MessageActionStateHandler.editableMessage.collectAsState()
	val replyMessage by MessageActionStateHandler.replyToMessage.collectAsState()
	if (chatChannelUiState is ChatChannelUiState.Success) {
		val topBar: @Composable () -> Unit = {
			AppBar(
				color = if (actionMessage != null) AppTheme.colors.surface else null,
				navIcon = {
					IconButton(
						onClick = {
							if (actionMessage != null) {
								MessageActionStateHandler.closeActionMode()
							} else {
								onBackClick()
							}
						}
					) {
						Icon(
							imageVector = Icons.Filled.ArrowBack,
							contentDescription = "cd_back",
							tint = AppTheme.colors.glow
						)
					}
				},
				title = {
					if (actionMessage == null) {
						ChatScreenAppBarTitle(
							userChannelInfo.first,
							chatChannelUiState.channel,
							userChannelInfo.second
						)
					}
				},
				actions = {
					if (actionMessage != null) {
						if (actionMessage?.isReply == false) {
							IconButton(onClick = { MessageActionStateHandler.replyToMessage() }) {
								Icon(
									painter = painterResource(R.drawable.ic_reply_24),
									contentDescription = "cd_message_action_reply",
									tint = Color.DarkGray
								)
							}
						}
						if (actionMessage!!.author.id == userChannelInfo.first) {
                            if (actionMessage!!.type == MessageType.TEXT) {
                                IconButton(onClick = { MessageActionStateHandler.editTextMessage() }) {
                                    Icon(
                                        imageVector = Icons.Filled.Edit,
                                        contentDescription = "cd_message_action_edit",
                                        tint = Color.DarkGray
                                    )
                                }
                            }
                            IconButton(
                                onClick = {
                                    actionMessage?.let(onDeleteMessage)
                                    MessageActionStateHandler.closeActionMode()
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "cd_message_action_delete",
                                    tint = Color.DarkGray
                                )
                            }
                        }
					} else {
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
				}
			)
		}
		Scaffold(topBar = { topBar() }) {
			Background {
				Column {
					MessagesContent(
						modifier = Modifier.weight(1f),
						userChannelInfo = userChannelInfo,
						uiState = messagesUiState
					)
					BottomBarDivider()
					replyMessage?.let {
						ReplyMessage(message = it) { MessageActionStateHandler.closeActionMode() }
					}
					if (isMemoRecording) {
						RecordMemoView(onCancel = onRecordMemo, onSendMemo = onSendMemo)
					} else {
						val userInputModifier = Modifier.navigationBarsPadding().weight(1f).imePadding()
						if (editableMessage != null) {
							UserInputPanel(
								modifier = userInputModifier,
								initialText = editableMessage?.message ?: "",
								onMessageSent = {
									if (editableMessage != null) {
										editableMessage?.copy(message = it)?.let(onEditMessage)
										MessageActionStateHandler.closeActionMode()
									} else onNewMessage(it)
								},
								addAttachment = {
									context.getActivity()?.let { showImagePicker(false, it, onPickImage) }
								},
								addImage = {
									context.getActivity()?.let { showImagePicker(true, it, onPickImage) }
								},
								recordMemo = onRecordMemo
							)
						} else {
							UserInputPanel(
								modifier = userInputModifier,
								onMessageSent = {
									if (replyMessage != null) {
										onReplyToMessage(replyMessage!!.id, it)
										MessageActionStateHandler.closeActionMode()
									} else onNewMessage(it)
								},
								addAttachment = {
									context.getActivity()?.let { showImagePicker(false, it, onPickImage) }
								},
								addImage = {
									context.getActivity()?.let { showImagePicker(true, it, onPickImage) }
								},
								recordMemo = onRecordMemo
							)
						}
					}
				}
			}
		}
	}
}

private fun showImagePicker(
	fromCamera: Boolean = false,
	activity: Activity,
	onImagePicker: (Intent) -> Unit
) {
	ImagePicker.with(activity).apply {
		if (fromCamera) cameraOnly() else galleryOnly()
		createIntent { onImagePicker(it) }
	}
}

@Preview @Composable
fun MessagesScreenPreview() = Preview {}
