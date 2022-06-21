package com.zero.android.data.conversion

import com.zero.android.models.FileThumbnail
import com.zero.android.models.Message
import com.zero.android.models.MessageReaction
import com.zero.android.network.model.ApiFileThumbnail
import com.zero.android.network.model.ApiMessage
import com.zero.android.network.model.ApiMessageReaction

internal fun ApiMessage.toModel(): Message =
	Message(
		id = id,
		channelUrl = channelUrl,
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
