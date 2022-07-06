package com.zero.android.data.conversion

import com.zero.android.database.model.MessageEntity
import com.zero.android.database.model.MessageWithRefs
import com.zero.android.models.FileThumbnail
import com.zero.android.models.Message
import com.zero.android.models.MessageReaction
import com.zero.android.network.model.ApiFileThumbnail
import com.zero.android.network.model.ApiMessage
import com.zero.android.network.model.ApiMessageReaction

internal fun ApiMessage.toModel(): Message =
	Message(
		id = id,
		channelId = channelId,
		author = author.toModel(),
		mentions = mentions.map { it.toModel() },
		type = type,
		mentionType = mentionType,
		createdAt = createdAt,
		updatedAt = updatedAt,
		status = status,
		data = data,
		message = message,
		parentMessage = parentMessage?.toModel(),
		isMuted = isMuted,
		fileUrl = fileUrl,
		fileName = fileName,
		fileThumbnails = fileThumbnails?.map { it.toModel() },
		fileMimeType = fileMimeType,
		reactions = reactions?.map { it.toModel() } ?: emptyList()
	)

internal fun ApiMessage.toEntity(): MessageWithRefs =
	MessageWithRefs(
		message =
		MessageEntity(
			id = id,
			channelId = channelId,
			authorId = author.id,
			parentMessageId = parentMessage?.id,
			parentMessageAuthorId = parentMessage?.author?.id,
			type = type,
			mentionType = mentionType,
			createdAt = createdAt,
			updatedAt = updatedAt,
			status = status,
			data = data,
			message = message,
			isMuted = isMuted,
			fileUrl = fileUrl,
			fileName = fileName,
			fileThumbnails = fileThumbnails?.map { it.toModel() },
			fileMimeType = fileMimeType,
			reactions = reactions?.map { it.toModel() } ?: emptyList()
		),
		author = author.toEntity(),
		mentions = mentions.map { it.toEntity() },
		parentMessage = parentMessage?.toEntity()?.message,
		parentMessageAuthor = parentMessage?.author?.toEntity()
	)

internal fun ApiFileThumbnail.toModel() =
	FileThumbnail(
		maxWidth = maxWidth,
		maxHeight = maxHeight,
		realWidth = realWidth,
		realHeight = realHeight,
		url = url
	)

internal fun ApiMessageReaction.toModel() =
	MessageReaction(messageId = messageId, key = key, userId = userId, updatedAt = updatedAt)
