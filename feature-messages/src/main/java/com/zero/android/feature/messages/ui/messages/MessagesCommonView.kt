package com.zero.android.feature.messages.ui.messages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.zero.android.common.util.SymbolAnnotationType
import com.zero.android.common.util.messageFormatter
import com.zero.android.feature.messages.ui.voicememo.VoiceMessage
import com.zero.android.models.Member
import com.zero.android.models.Message
import com.zero.android.models.enums.MessageType
import com.zero.android.ui.theme.AppTheme
import com.zero.android.ui.theme.White

@Composable
fun ColumnScope.MessageContent(message: Message, authorClicked: (Member) -> Unit) {
    when (message.type) {
        MessageType.IMAGE -> message.fileUrl?.let {
            AsyncImage(
                model = it,
                contentDescription = "",
                modifier = Modifier
                    .wrapContentWidth()
                    .defaultMinSize(160.dp)
            )
        }
        MessageType.AUDIO -> VoiceMessage(message)
        else -> message.message?.let {
            ClickableMessage(message = message, authorClicked = authorClicked)
        }
    }
}

@Composable
fun ColumnScope.ChatBubbleSpacing(isFirstMessageByAuthor: Boolean) {
    if (isFirstMessageByAuthor) {
        // Last bubble before next author
        Spacer(modifier = Modifier.height(6.dp))
    } else {
        // Between bubbles
        Spacer(modifier = Modifier.height(2.dp))
    }
}

@Composable
private fun ClickableMessage(message: Message, authorClicked: (Member) -> Unit) {
    val uriHandler = LocalUriHandler.current
    val styledMessage =
        (message.message ?: "").messageFormatter(annotationColor = AppTheme.colors.glow)
    ClickableText(
        text = styledMessage,
        style = MaterialTheme.typography.bodyLarge.copy(color = White),
        onClick = {
            styledMessage.getStringAnnotations(start = it, end = it).firstOrNull()?.let { annotation ->
                when (annotation.tag) {
                    SymbolAnnotationType.LINK.name -> uriHandler.openUri(annotation.item)
                    SymbolAnnotationType.PERSON.name -> authorClicked(message.author)
                    else -> Unit
                }
            }
        }
    )
}
