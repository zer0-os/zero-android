package com.zero.android.feature.messages.ui.messages

import android.content.Context
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.github.dhaval2404.imagepicker.ImagePicker
import com.zero.android.common.R
import com.zero.android.common.extensions.*
import com.zero.android.common.util.SymbolAnnotationType
import com.zero.android.common.util.messageFormatter
import com.zero.android.models.Member
import com.zero.android.models.Message
import com.zero.android.ui.components.*
import com.zero.android.ui.theme.AppTheme
import com.zero.android.ui.theme.Gray
import com.zero.android.ui.theme.White
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagesContent(
    modifier: Modifier = Modifier,
    userChannelInfo: Pair<String, Boolean>,
    uiState: MessagesUiState,
    imageSelectorLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>,
    onNewMessage:(String) -> Unit,
) {
    val scrollState = rememberLazyListState()
    val scrollBehavior = remember { TopAppBarDefaults.pinnedScrollBehavior() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Surface(modifier = modifier) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(AppTheme.colors.surfaceInverse)
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
            ) {
                Messages(
                    modifier = Modifier.weight(1f),
                    userChannelInfo = userChannelInfo,
                    uiState = uiState,
                    scrollState = scrollState,
                    coroutineScope = scope
                )
                BottomBarDivider()
                UserInputPanel(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .weight(1f)
                        .imePadding(),
                    onMessageSent = { onNewMessage(it) },
                    resetScroll = {
                        scope.launch {
                            scrollState.scrollToItem(0)
                        }
                    },
                    addAttachment = {
                        context.getActivity()?.let {
                            ImagePicker.with(it)
                                .galleryOnly()
                                .createIntent { intent ->
                                    imageSelectorLauncher.launch(intent)
                                }
                        }
                    },
                    addImage = {
                        context.getActivity()?.let {
                            ImagePicker.with(it)
                                .cameraOnly()
                                .createIntent { intent ->
                                    imageSelectorLauncher.launch(intent)
                                }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun Messages(
    modifier: Modifier = Modifier,
    userChannelInfo: Pair<String, Boolean>,
    uiState: MessagesUiState,
    scrollState: LazyListState,
    coroutineScope: CoroutineScope,
) {
    Box(modifier = modifier.padding(14.dp)) {
        if (uiState is MessagesUiState.Success) {
            val messages = uiState.messages
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                reverseLayout = true,
                state = scrollState,
                contentPadding = WindowInsets.statusBars.add(WindowInsets(top = 90.dp))
                    .asPaddingValues()
            ) {
                for (index in messages.indices) {
                    val prevAuthor = messages.getOrNull(index - 1)?.author
                    val nextAuthor = messages.getOrNull(index + 1)?.author
                    val content = messages[index]
                    val messageDate = content.createdAt.toDate()
                    val nextMessageDate = (messages.getOrNull(index + 1)?.createdAt ?: 0).toDate()
                    val isSameDay = nextMessageDate.isSameDay(messageDate)
                    val isFirstMessageByAuthor = prevAuthor != content.author
                    val isLastMessageByAuthor = nextAuthor != content.author

                    item {
                        if (!userChannelInfo.second) {
                            DirectMessage(
                                msg = content,
                                isUserMe = content.author.id == userChannelInfo.first,
                                isFirstMessageByAuthor = isFirstMessageByAuthor,
                                isLastMessageByAuthor = isLastMessageByAuthor,
                                onAuthorClick = {

                                }
                            )
                        } else {
                            ChannelMessage(
                                msg = content,
                                isUserMe = content.author.id == userChannelInfo.first,
                                isFirstMessageByAuthor = isFirstMessageByAuthor,
                                onAuthorClick = {

                                }
                            )
                        }
                    }
                    if (!isSameDay) {
                        item { DayHeader(messageDate.format("MMMM dd, yyyy")) }
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

@Composable
fun DirectMessage(
    onAuthorClick: (Member) -> Unit,
    msg: Message,
    isUserMe: Boolean,
    isFirstMessageByAuthor: Boolean,
    isLastMessageByAuthor: Boolean
) {
    val modifier = if (isLastMessageByAuthor) Modifier.padding(top = 8.dp) else Modifier
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = if (isUserMe) Arrangement.End else Arrangement.Start,
        ) {
            if (!isUserMe) {
                if (isLastMessageByAuthor) {
                    SmallCircularImage(
                        imageUrl = msg.author.profileImage,
                        placeHolder = R.drawable.ic_user_profile_placeholder
                    )
                } else {
                    Spacer(modifier = Modifier.width(34.dp))
                }
            }
            DirectMessageAuthorAndTextMessage(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .weight(1f),
                message = msg,
                isUserMe = isUserMe,
                isFirstMessageByAuthor = isFirstMessageByAuthor,
                isLastMessageByAuthor = isLastMessageByAuthor,
                authorClicked = onAuthorClick
            )
        }
    }
}

@Composable
fun ChannelMessage(
    onAuthorClick: (Member) -> Unit,
    msg: Message,
    isUserMe: Boolean,
    isFirstMessageByAuthor: Boolean
) {
    Row {
        SmallCircularImage(
            imageUrl = msg.author.profileImage,
            placeHolder = R.drawable.ic_user_profile_placeholder
        )
        GroupChannelAuthorAndTextMessage(
            modifier = Modifier
                .padding(end = 16.dp)
                .weight(1f),
            message = msg,
            isUserMe = isUserMe,
            isFirstMessageByAuthor = isFirstMessageByAuthor,
            authorClicked = onAuthorClick
        )
    }
}

private val ChatBubbleShape = RoundedCornerShape(4.dp, 12.dp, 12.dp, 12.dp)
private val ChatDirectOther = RoundedCornerShape(12.dp, 12.dp, 12.dp, 4.dp)
private val ChatDirectAuthor = RoundedCornerShape(12.dp, 12.dp, 4.dp, 12.dp)
private val ChatDirectSame = RoundedCornerShape(12.dp, 12.dp, 12.dp, 12.dp)

@Composable
fun DirectMessageAuthorAndTextMessage(
    modifier: Modifier = Modifier,
    message: Message,
    isUserMe: Boolean,
    isFirstMessageByAuthor: Boolean,
    isLastMessageByAuthor: Boolean,
    authorClicked: (Member) -> Unit
) {
    val backgroundColorsList = if (isUserMe) {
        listOf(Color(0xFF470080), Color(0xFFB14EFF))
    } else {
        listOf(Color(0xFF191919), Color(0xFF0A0A0A))
    }
    Column {
        Row {
            Spacer(modifier = Modifier.width(12.dp))
            Box(
                modifier = Modifier.background(
                    brush = Brush.linearGradient(
                        colors = backgroundColorsList
                    ),
                    shape = if (isUserMe) ChatDirectAuthor else ChatDirectOther
                )
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    if (!isUserMe && isLastMessageByAuthor) {
                        Text(
                            text = message.author.name ?: "",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier =
                            Modifier.paddingFrom(LastBaseline, after = 8.dp) // Space to 1st bubble
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    MessageContent(
                        message = message,
                        authorClicked = authorClicked
                    )
                    val messageDate = message.createdAt.toDate()
                    Text(
                        text = messageDate.format("hh:mm aa"),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.align(Alignment.End),
                        color = AppTheme.colors.colorTextSecondary,
                    )
                }
            }
        }
        ChatBubbleSpacing(isFirstMessageByAuthor)
    }
}

@Composable
fun GroupChannelAuthorAndTextMessage(
    modifier: Modifier = Modifier,
    message: Message,
    isUserMe: Boolean,
    isFirstMessageByAuthor: Boolean,
    authorClicked: (Member) -> Unit
) {
    val backgroundColorsList = if (isUserMe) {
        listOf(Color(0xFF470080), Color(0xFFB14EFF))
    } else {
        listOf(Color(0xFF191919), Color(0xFF0A0A0A))
    }
    Column {
        Row {
            Spacer(modifier = Modifier.width(12.dp))
            Box(
                modifier = Modifier.background(
                    brush = Brush.linearGradient(
                        colors = backgroundColorsList
                    ),
                    shape = ChatBubbleShape
                )
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    AuthorNameTimestamp(isUserMe, message)
                    MessageContent(
                        message = message,
                        authorClicked = authorClicked
                    )
                }
            }
        }
        ChatBubbleSpacing(isFirstMessageByAuthor)
    }
}

@Composable
private fun AuthorNameTimestamp(isUserMe: Boolean, msg: Message) {
    Row(modifier = Modifier.semantics(mergeDescendants = true) {}) {
        Text(
            text = if (isUserMe) "Me" else msg.author.name ?: "",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier =
            Modifier
                .alignBy(LastBaseline)
                .paddingFrom(LastBaseline, after = 8.dp) // Space to 1st bubble
        )
        Spacer(modifier = Modifier.width(8.dp))
        val messageDate = msg.createdAt.toDate()
        Text(
            text = "${messageDate.toMessageDateFormat()} at ${messageDate.format("hh:mm aa")}",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.alignBy(LastBaseline),
            color = Gray,
        )
    }
}

@Composable
private fun ColumnScope.MessageContent(
    message: Message,
    authorClicked: (Member) -> Unit,
) {
    message.message?.let {
        if (message.fileUrl.isNullOrEmpty()) {
            ClickableMessage(
                message = message,
                authorClicked = authorClicked
            )
        }
    }
    message.fileUrl?.let {
        AsyncImage(
            model = it,
            contentDescription = "",
            modifier = Modifier
                .wrapContentWidth()
                .defaultMinSize(160.dp),
        )
    }
}

@Composable
private fun ColumnScope.ChatBubbleSpacing(isFirstMessageByAuthor: Boolean){
    if (isFirstMessageByAuthor) {
        // Last bubble before next author
        Spacer(modifier = Modifier.height(8.dp))
    } else {
        // Between bubbles
        Spacer(modifier = Modifier.height(4.dp))
    }
}

@Composable
fun ClickableMessage(message: Message, authorClicked: (Member) -> Unit) {
    val uriHandler = LocalUriHandler.current
    val styledMessage = (message.message ?: "").messageFormatter(annotationColor = AppTheme.colors.glow)
    ClickableText(
        text = styledMessage,
        style = MaterialTheme.typography.bodyLarge.copy(color = White),
        onClick = {
            styledMessage.getStringAnnotations(start = it, end = it).firstOrNull()
                ?.let { annotation ->
                    when (annotation.tag) {
                        SymbolAnnotationType.LINK.name -> uriHandler.openUri(annotation.item)
                        SymbolAnnotationType.PERSON.name -> authorClicked(message.author)
                        else -> Unit
                    }
                }
        }
    )
}

@Preview
@Composable
fun ConversationPreview() {
}

private val JumpToBottomThreshold = 56.dp
