package com.zero.android.feature.messages.util

import com.zero.android.models.DraftMessage
import com.zero.android.models.Member
import com.zero.android.models.enums.MessageMentionType
import com.zero.android.models.enums.MessageStatus
import com.zero.android.models.enums.MessageType
import java.io.File
import java.util.*

object MessageUtil {

	fun newTextMessage(msg: String, authorId: String) =
		DraftMessage(
			channelId = "",
			author = Member(authorId),
			type = MessageType.TEXT,
			mentionType = MessageMentionType.USER,
			message = msg,
			createdAt = Calendar.getInstance().timeInMillis,
			updatedAt = Calendar.getInstance().timeInMillis,
			status = MessageStatus.SUCCEEDED
		)

	fun newFileMessage(file: File, authorId: String, type: MessageType) =
		DraftMessage(
			channelId = "",
			author = Member(authorId),
			type = type,
			mentionType = MessageMentionType.USER,
			file = file,
			fileMimeType = type.serializedName,
			createdAt = Calendar.getInstance().timeInMillis,
			updatedAt = Calendar.getInstance().timeInMillis,
			status = MessageStatus.SUCCEEDED
		)
}
