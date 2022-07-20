package com.zero.android.feature.messages.ui.messages

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.github.dhaval2404.imagepicker.ImagePicker
import com.zero.android.common.extensions.format
import com.zero.android.common.extensions.getActivity
import com.zero.android.common.extensions.isSameDay
import com.zero.android.common.extensions.toDate
import com.zero.android.common.ui.Result
import com.zero.android.feature.messages.ui.voicememo.RecordMemoView
import com.zero.android.models.Message
import com.zero.android.ui.components.BottomBarDivider
import com.zero.android.ui.components.DayHeader
import com.zero.android.ui.components.JumpToBottom
import com.zero.android.ui.theme.AppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagesContent(
	modifier: Modifier = Modifier,
	userChannelInfo: Pair<String, Boolean>,
	uiState: MessagesUIState,
	messages: LazyPagingItems<Message>,
	isMemoRecording: Boolean,
	onNewMessage: (String) -> Unit,
	onImagePicker: (Intent) -> Unit,
	onMemoRecorder: () -> Unit,
	onSendMemo: () -> Unit
) {
	val scrollState = rememberLazyListState()
	val scrollBehavior = remember { TopAppBarDefaults.pinnedScrollBehavior() }
	val scope = rememberCoroutineScope()
	val context = LocalContext.current

	Surface(modifier = modifier) {
		Box(modifier = Modifier.fillMaxSize()) {
			Column(
				Modifier.fillMaxSize()
					.background(AppTheme.colors.surfaceInverse)
					.nestedScroll(scrollBehavior.nestedScrollConnection)
			) {
				Messages(
					modifier = Modifier.weight(1f),
					userChannelInfo = userChannelInfo,
					uiState = uiState,
					messages = messages,
					scrollState = scrollState,
					coroutineScope = scope
				)
				BottomBarDivider()
				if (isMemoRecording) {
					RecordMemoView(onCancel = onMemoRecorder, onSendMemo = onSendMemo)
				} else {
					UserInputPanel(
						modifier = Modifier.navigationBarsPadding().weight(1f).imePadding(),
						onMessageSent = { onNewMessage(it) },
						resetScroll = { scope.launch { scrollState.scrollToItem(0) } },
						addAttachment = {
							context.getActivity()?.let { showImagePicker(false, it, onImagePicker) }
						},
						addImage = {
							context.getActivity()?.let { showImagePicker(true, it, onImagePicker) }
						},
						recordMemo = onMemoRecorder
					)
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

@Composable
fun Messages(
	modifier: Modifier = Modifier,
	userChannelInfo: Pair<String, Boolean>,
	uiState: MessagesUIState,
	messages: LazyPagingItems<Message>,
	scrollState: LazyListState,
	coroutineScope: CoroutineScope
) {
	Box(modifier = modifier.padding(14.dp)) {
		if (uiState is Result.Success) {
			LazyColumn(
				modifier = Modifier.fillMaxSize(),
				reverseLayout = true,
				state = scrollState,
				contentPadding =
				WindowInsets.statusBars.add(WindowInsets(top = 90.dp)).asPaddingValues()
			) {
				items(messages) { content ->
					content as Message
					val index = messages.itemSnapshotList.items.indexOf(content)

					val prevAuthor = messages[index - 1]?.author
					val nextAuthor = messages[index + 1]?.author
					val messageDate = content.createdAt.toDate()
					val nextMessageDate = (messages[index + 1]?.createdAt ?: 0).toDate()
					val isSameDay = nextMessageDate.isSameDay(messageDate)
					val isFirstMessageByAuthor = prevAuthor != content.author
					val isLastMessageByAuthor = nextAuthor != content.author

					if (!userChannelInfo.second) {
						DirectMessage(
							msg = content,
							isUserMe = content.author.id == userChannelInfo.first,
							isSameDay = isSameDay,
							isFirstMessageByAuthor = isFirstMessageByAuthor,
							isLastMessageByAuthor = isLastMessageByAuthor,
							onAuthorClick = {}
						)
					} else {
						ChannelMessage(
							msg = content,
							isUserMe = content.author.id == userChannelInfo.first,
							isFirstMessageByAuthor = isFirstMessageByAuthor,
							onAuthorClick = {}
						)
					}

					if (!isSameDay) {
						DayHeader(messageDate.format("MMMM dd, yyyy"))
					}
				}
			}

			val jumpThreshold = with(LocalDensity.current) { JumpToBottomThreshold.toPx() }
			val jumpToBottomButtonEnabled by remember {
				derivedStateOf {
					scrollState.firstVisibleItemIndex != 0 ||
						scrollState.firstVisibleItemScrollOffset > jumpThreshold
				}
			}
			JumpToBottom(
				// Only show if the scroller is not at the bottom
				enabled = jumpToBottomButtonEnabled,
				onClicked = { coroutineScope.launch { scrollState.animateScrollToItem(0) } },
				modifier = Modifier.align(Alignment.BottomCenter)
			)
		}
	}
}

@Preview @Composable
fun ConversationPreview() {}

private val JumpToBottomThreshold = 56.dp
