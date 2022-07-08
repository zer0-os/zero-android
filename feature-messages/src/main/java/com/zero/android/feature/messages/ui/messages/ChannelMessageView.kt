package com.zero.android.feature.messages.ui.messages

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.zero.android.common.R
import com.zero.android.common.extensions.format
import com.zero.android.common.extensions.toDate
import com.zero.android.common.extensions.toMessageDateFormat
import com.zero.android.feature.messages.ui.voicememo.mediaPlayer.MediaSourceViewModel
import com.zero.android.models.Member
import com.zero.android.models.Message
import com.zero.android.ui.components.SmallCircularImage
import com.zero.android.ui.theme.Gray

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChannelMessage(
    msg: Message,
    isUserMe: Boolean,
    isFirstMessageByAuthor: Boolean,
    mediaSourceViewModel: MediaSourceViewModel,
    onAuthorClick: (Member) -> Unit,
    onMessageLongClick:(Message) -> Unit,
) {
    Row(modifier = Modifier
        .combinedClickable(
            onClick = { },
            onLongClick = { onMessageLongClick(msg) }
        )) {
        SmallCircularImage(
            imageUrl = msg.author.profileImage,
            placeHolder = R.drawable.ic_user_profile_placeholder
        )
        CMAuthorAndTextMessage(
            modifier = Modifier
                .padding(end = 16.dp)
                .weight(1f),
            message = msg,
            isUserMe = isUserMe,
            isFirstMessageByAuthor = isFirstMessageByAuthor,
            authorClicked = onAuthorClick,
            mediaSourceViewModel = mediaSourceViewModel
        )
    }
}

private val ChatBubbleShape = RoundedCornerShape(4.dp, 12.dp, 12.dp, 12.dp)

@Composable
fun CMAuthorAndTextMessage(
    modifier: Modifier = Modifier,
    message: Message,
    isUserMe: Boolean,
    mediaSourceViewModel: MediaSourceViewModel,
    isFirstMessageByAuthor: Boolean,
    authorClicked: (Member) -> Unit
) {
    val backgroundColorsList =
        if (isUserMe) {
            listOf(Color(0xFF470080), Color(0xFFB14EFF))
        } else {
            listOf(Color(0xFF191919), Color(0xFF0A0A0A))
        }
    Column {
        Row {
            Spacer(modifier = Modifier.width(12.dp))
            Box(
                modifier =
                Modifier.background(
                    brush = Brush.linearGradient(colors = backgroundColorsList),
                    shape = ChatBubbleShape
                )
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    AuthorNameTimestamp(isUserMe, message)
                    MessageContent(message = message, authorClicked = authorClicked, mediaSourceViewModel = mediaSourceViewModel)
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
            color = Gray
        )
    }
}
