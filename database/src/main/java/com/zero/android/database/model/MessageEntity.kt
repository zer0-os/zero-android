package com.zero.android.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.zero.android.models.FileThumbnail
import com.zero.android.models.Member
import com.zero.android.models.Message
import com.zero.android.models.MessageReaction
import com.zero.android.models.enums.MessageMentionType
import com.zero.android.models.enums.MessageStatus
import com.zero.android.models.enums.MessageType

@Entity(
	tableName = "messages",
	foreignKeys =
	[
		ForeignKey(
			entity = ChannelEntity::class,
			parentColumns = ["id"],
			childColumns = ["channelId"],
			onDelete = ForeignKey.CASCADE
		)
	]
)
data class MessageEntity(
	@PrimaryKey val id: String,
	val channelId: String,
	val type: MessageType,
	val mentionType: MessageMentionType,
	val message: String? = null,
	val createdAt: Long,
	val updatedAt: Long,
	val status: MessageStatus,
	val data: String? = null,
	val isMuted: Boolean = false,
	val fileUrl: String? = null,
	val fileName: String? = null,
	val fileThumbnails: List<FileThumbnail>? = null,
	val fileMimeType: String? = null,
	val reactions: List<MessageReaction> = emptyList()
)

fun MessageEntity.toModel(author: Member) =
	Message(
		id = id,
		channelId = channelId,
		type = type,
		mentionType = mentionType,
		message = message,
		createdAt = createdAt,
		updatedAt = updatedAt,
		status = status,
		data = data,
		isMuted = isMuted,
		fileUrl = fileUrl,
		fileName = fileName,
		fileThumbnails = fileThumbnails,
		fileMimeType = fileMimeType,
		reactions = reactions,
		author = author
	)
