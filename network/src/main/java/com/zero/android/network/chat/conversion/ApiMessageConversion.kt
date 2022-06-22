/* ktlint-disable filename */
package com.zero.android.network.chat.conversion

import com.zero.android.models.FileThumbnail
import com.zero.android.models.Message
import com.zero.android.models.MessageReaction
import com.zero.android.network.model.ApiFileThumbnail
import com.zero.android.network.model.ApiMessage
import com.zero.android.network.model.ApiMessageReaction

internal fun Message.toApi(): ApiMessage =
	ApiMessage(
		id = id,
		channelUrl = channelUrl,
		author = author.toApi(),
		mentions = mentions.map { it.toApi() },
		type = type,
		mentionType = mentionType,
		createdAt = createdAt,
		updatedAt = updatedAt,
		status = status,
		data = data,
		parentMessage = parentMessage?.toApi(),
		isMuted = isMuted,
		fileUrl = fileUrl,
		fileName = fileName,
		fileThumbnails = fileThumbnails?.map { it.toApi() },
		fileMimeType = fileMimeType,
		reactions = reactions.map { it.toApi() }
	)

internal fun FileThumbnail.toApi() =
	ApiFileThumbnail(
		maxWidth = maxWidth,
		maxHeight = maxHeight,
		realWidth = realWidth,
		realHeight = realHeight,
		url = url
	)

internal fun MessageReaction.toApi() =
	ApiMessageReaction(messageId = messageId, key = key, userId = userId, updatedAt = updatedAt)
