package com.zero.android.feature.messages.ui.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.unit.dp
import com.zero.android.common.R
import com.zero.android.common.extensions.format
import com.zero.android.common.extensions.toDate
import com.zero.android.feature.messages.ui.voicememo.mediaPlayer.MediaSourceViewModel
import com.zero.android.models.Member
import com.zero.android.models.Message
import com.zero.android.ui.components.SmallCircularImage
import com.zero.android.ui.theme.AppTheme

@Composable
fun DirectMessage(
    onAuthorClick: (Member) -> Unit,
    msg: Message,
    isUserMe: Boolean,
    isSameDay: Boolean,
    isFirstMessageByAuthor: Boolean,
    isLastMessageByAuthor: Boolean,
    mediaSourceViewModel: MediaSourceViewModel
) {
    val modifier = if (isLastMessageByAuthor) Modifier.padding(top = 8.dp) else Modifier
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = if (isUserMe) Arrangement.End else Arrangement.Start
        ) {
            if (!isUserMe && (isLastMessageByAuthor || !isSameDay)) {
                SmallCircularImage(
                    modifier = Modifier
                        .align(Alignment.Bottom)
                        .padding(bottom = 4.dp),
                    imageUrl = msg.author.profileImage,
                    placeHolder = R.drawable.ic_user_profile_placeholder
                )
            } else {
                Spacer(modifier = Modifier.width(36.dp))
            }
            DMAuthorAndTextMessage(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .weight(1f),
                message = msg,
                isUserMe = isUserMe,
                isSameDay = isSameDay,
                isFirstMessageByAuthor = isFirstMessageByAuthor,
                isLastMessageByAuthor = isLastMessageByAuthor,
                authorClicked = onAuthorClick,
                mediaSourceViewModel = mediaSourceViewModel
            )
        }
    }
}

private val ChatDirectOther = RoundedCornerShape(12.dp, 12.dp, 12.dp, 4.dp)
private val ChatDirectAuthor = RoundedCornerShape(12.dp, 12.dp, 4.dp, 12.dp)
private val ChatDirectSame = RoundedCornerShape(8.dp, 8.dp, 8.dp, 8.dp)

@Composable
fun DMAuthorAndTextMessage(
    modifier: Modifier = Modifier,
    message: Message,
    isUserMe: Boolean,
    isSameDay: Boolean,
    isFirstMessageByAuthor: Boolean,
    isLastMessageByAuthor: Boolean,
    mediaSourceViewModel: MediaSourceViewModel,
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
                    shape = if (isLastMessageByAuthor || !isSameDay) {
                        if (isUserMe) ChatDirectAuthor else ChatDirectOther
                    } else ChatDirectSame
                )
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    if (!isUserMe && (isLastMessageByAuthor || !isSameDay)) {
                        Text(
                            text = message.author.name ?: "",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.paddingFrom(LastBaseline, after = 8.dp) // Space to 1st bubble
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    MessageContent(message = message, authorClicked = authorClicked, mediaSourceViewModel = mediaSourceViewModel)
                    val messageDate = message.createdAt.toDate()
                    Text(
                        text = messageDate.format("hh:mm aa"),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.align(Alignment.End),
                        color = AppTheme.colors.colorTextSecondary
                    )
                }
            }
        }
        ChatBubbleSpacing(isFirstMessageByAuthor)
    }
}
