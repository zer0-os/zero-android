package com.zero.android.database.model

import androidx.room.DatabaseView
import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.zero.android.models.Message

@DatabaseView("SELECT * FROM messages")
data class MessageWithRefs(
	@Embedded val message: MessageEntity,
	@Relation(parentColumn = "authorId", entityColumn = "id") val author: MemberEntity,
	@Relation(parentColumn = "parentMessageId", entityColumn = "id")
	val parentMessage: MessageEntity? = null,
	@Relation(parentColumn = "parentMessageAuthorId", entityColumn = "id")
	val parentMessageAuthor: MemberEntity? = null,
	@Relation(
		parentColumn = "id",
		entityColumn = "id",
		associateBy =
		Junction(
			value = MessageMentionCrossRef::class,
			parentColumn = "messageId",
			entityColumn = "memberId"
		)
	)
	val mentions: List<MemberEntity>? = null
)

fun MessageWithRefs.toModel() =
	Message(
		id = message.id,
		author = author.toModel(),
		mentions = mentions?.map { it.toModel() } ?: emptyList(),
		parentMessage =
		parentMessageAuthor?.let { author -> parentMessage?.toModel(author.toModel()) },
		channelId = message.channelId,
		type = message.type,
		mentionType = message.mentionType,
		message = message.message,
		createdAt = message.createdAt,
		updatedAt = message.updatedAt,
		status = message.status,
		data = message.data,
		isMuted = message.isMuted,
		fileUrl = message.fileUrl,
		fileName = message.fileName,
		fileThumbnails = message.fileThumbnails,
		fileMimeType = message.fileMimeType,
		reactions = message.reactions
	)
