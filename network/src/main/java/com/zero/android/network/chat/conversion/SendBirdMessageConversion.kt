package com.zero.android.network.chat.conversion

import android.util.Base64
import com.sendbird.android.*
import com.sendbird.android.ReactionEvent.ReactionEventAction
import com.sendbird.android.constant.StringSet.value
import com.zero.android.models.DraftMessage
import com.zero.android.models.FileThumbnail
import com.zero.android.models.enums.*
import com.zero.android.network.model.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject
import okio.internal.commonAsUtf8ToByteArray

private fun ApiMessage.toSendBirdJsonString(): ByteArray {
	val json =
		Json.encodeToJsonElement(this).run {
			jsonObject.apply { plus(Pair("message_id", id.toLong())) }
			Json.encodeToString(this)
		}
	return Base64.decode(json.commonAsUtf8ToByteArray(), 0)
}

internal fun BaseMessage.toApi(): ApiMessage {
    val data = if (this is FileMessage && data.isNotEmpty()) {
        Json { ignoreUnknownKeys = true }.decodeFromString<ApiFileData?>(data)
    } else null
    return ApiMessage(
        id = messageId.toString(),
        type = if (this is FileMessage) data?.type.toMessageType()
        else customType.toMessageType(),
        mentionType = mentionType.toType(),
        channelUrl = channelUrl,
        author = sender.toApi(),
        status = sendingStatus.toType(),
        createdAt = createdAt,
        updatedAt = updatedAt,
        message = message,
        parentMessage = parentMessage?.toApi(),
        fileUrl = (this as? FileMessage)?.url,
        fileName = (this as? FileMessage)?.name,
    )
}

internal fun UserMessage.toApi() =
	ApiMessage(
		id = messageId.toString(),
		channelUrl = channelUrl,
		author = sender.toApi(),
		mentions = mentionedUsers.map { it.toApi() },
		type = customType.toMessageType(),
		mentionType = mentionType.toType(),
		createdAt = createdAt,
		updatedAt = updatedAt,
		status = sendingStatus.toType(),
		data = data,
		parentMessage = parentMessage?.toApi(),
		isMuted = isSilent,
		message = message
	)

internal fun FileMessage.toApi() =
	ApiMessage(
		id = messageId.toString(),
		channelUrl = channelUrl,
		author = sender.toApi(),
		mentions = mentionedUsers.map { it.toApi() },
		type = type.toMessageType(),
		mentionType = mentionType.toType(),
		createdAt = createdAt,
		updatedAt = updatedAt,
		status = sendingStatus.toType(),
		data = data,
		parentMessage = parentMessage?.toApi(),
		isMuted = isSilent,
		fileUrl = url,
		fileName = name,
		fileThumbnails = thumbnails.map { it.toApi() },
		fileMimeType = messageParams?.mimeType
	)

internal fun FileMessage.Thumbnail.toApi() =
	ApiFileThumbnail(
		maxWidth = maxWidth,
		maxHeight = maxHeight,
		realWidth = realWidth,
		realHeight = realHeight,
		url = url
	)

internal fun ApiMessage.toMessage(): BaseMessage {
	return if (type == MessageType.TEXT) {
		UserMessage.buildFromSerializedData(toSendBirdJsonString())
	} else {
		FileMessage.buildFromSerializedData(toSendBirdJsonString())
	}
}

internal fun DraftMessage.toParams(): BaseMessageParams {
	return if (type == MessageType.TEXT) {
		UserMessageParams().also { params ->
			params.message = message!!
			params.data = data
			parentMessageId?.let { params.parentMessageId = it.toLong() }
			params.customType = type.serializedName
			params.mentionedUserIds = mentions
		}
	} else {
		FileMessageParams().also { params ->
			params.data = data
			parentMessageId?.let { params.parentMessageId = it.toLong() }
			params.customType = type.serializedName
			params.mentionedUserIds = mentions

			params.file = file
			params.fileUrl = fileUrl
			params.fileName = fileName
			params.thumbnailSizes = fileThumbnails?.map { it.toSize() }
			params.mimeType = fileMimeType
		}
	}
}

internal fun ReactionEvent.toApi() =
	ApiMessageReaction(messageId = messageId, key = key, userId = userId, updatedAt = updatedAt)

internal fun FileThumbnail.toSize() = FileMessage.ThumbnailSize(maxWidth, maxHeight)

internal fun BaseMessageParams.MentionType.toType() = value.toMessageMentionType()

internal fun MessageMentionType.toType() = BaseMessageParams.MentionType.valueOf(serializedName)

internal fun BaseMessage.SendingStatus.toType() = value.toMessageStatus()

internal fun MessageStatus.toStatus() = MessageStatus.valueOf(serializedName)

internal fun ReactionEventAction.toType() = value.lowercase().toMessageReactionAction()
