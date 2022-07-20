package com.zero.android.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
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
	indices = [Index("channelId"), Index("authorId"), Index("parentMessageId")],
	foreignKeys =
	[
		ForeignKey(
			entity = ChannelEntity::class,
			parentColumns = ["id"],
			childColumns = ["channelId"],
			onDelete = ForeignKey.CASCADE
		),
		ForeignKey(
			entity = MemberEntity::class,
			parentColumns = ["id"],
			childColumns = ["authorId"],
			onDelete = ForeignKey.RESTRICT
		),
		ForeignKey(
			entity = MessageEntity::class,
			parentColumns = ["id"],
			childColumns = ["parentMessageId"],
			onDelete = ForeignKey.SET_NULL
		),
		ForeignKey(
			entity = MemberEntity::class,
			parentColumns = ["id"],
			childColumns = ["parentMessageAuthorId"],
			onDelete = ForeignKey.SET_NULL
		)
	]
)
data class MessageEntity(
	@PrimaryKey override val id: String,
	val authorId: String,
	val parentMessageId: String? = null,
	val parentMessageAuthorId: String? = null,
	val channelId: String,
	val type: MessageType,
	val mentionType: MessageMentionType = MessageMentionType.UNKNOWN,
	val message: String? = null,
	val createdAt: Long = 0,
	val updatedAt: Long = 0,
	val status: MessageStatus,
	val data: String? = null,
	val isMuted: Boolean = false,
	val fileUrl: String? = null,
	val fileName: String? = null,
	val fileThumbnails: List<FileThumbnail>? = null,
	val fileMimeType: String? = null,
	val reactions: List<MessageReaction> = emptyList()
) : BaseEntity

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
