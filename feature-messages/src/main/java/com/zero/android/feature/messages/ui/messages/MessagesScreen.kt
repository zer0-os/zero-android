package com.zero.android.feature.messages.ui.messages

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toFile
import androidx.hilt.navigation.compose.hiltViewModel
import com.zero.android.common.R
import com.zero.android.common.extensions.toFile
import com.zero.android.feature.messages.ui.voicememo.MemoRecorderViewModel
import com.zero.android.models.enums.MessageType
import com.zero.android.ui.components.AppBar
import com.zero.android.ui.components.Background
import com.zero.android.ui.extensions.Preview
import com.zero.android.ui.theme.AppTheme
import java.io.File

@Composable
fun MessagesRoute(
    onBackClick: () -> Unit,
    viewModel: MessagesViewModel = hiltViewModel(),
    recordMemoViewModel: MemoRecorderViewModel = hiltViewModel(),
) {
    val chatUiState: ChatScreenUiState by viewModel.uiState.collectAsState()
    val recordingState: Boolean by recordMemoViewModel.recordingState.collectAsState()
    val userChannelInfo = viewModel.loggedInUserId to viewModel.isGroupChannel
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loadChannel()
    }

    val imageSelectorLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            val resultCode = it.resultCode
            val data = it.data
            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!
                val file = try { fileUri.toFile() }
                catch (e: Exception) { fileUri.toFile(context) }
                viewModel.sendMessage(chatUiState.newFileMessage(
                    file = file,
                    currentUserId = userChannelInfo.first,
                    type = MessageType.IMAGE
                ))
            }
        }
    )
    val recordMemoPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (granted) recordMemoViewModel.startRecording()
        }
    )

    MessagesScreen(
        onBackClick,
        userChannelInfo,
        chatUiState.channelUiState,
        chatUiState.messagesUiState,
        recordingState,
        onNewMessage = { newMessage ->
            viewModel.sendMessage(chatUiState.newTextMessage(
                msg = newMessage,
                currentUserId = userChannelInfo.first
            ))
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
                viewModel.sendMessage(chatUiState.newFileMessage(
                    file = file,
                    currentUserId = userChannelInfo.first,
                    type = MessageType.AUDIO
                ))
            }
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
    onPickImage:(Intent) -> Unit,
    onRecordMemo: () -> Unit,
    onSendMemo:() -> Unit,
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
                    ChatScreenAppBarTitle(
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
                    onNewMessage = onNewMessage,
                    onImagePicker = onPickImage,
                    isMemoRecording = isMemoRecording,
                    onMemoRecorder = onRecordMemo,
                    onSendMemo = onSendMemo
                )
            }
        }
    }
}

@Preview @Composable
fun MessagesScreenPreview() = Preview {}
