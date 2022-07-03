package com.zero.android.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
	tableName = "message_mentions_relation",
	primaryKeys = ["messageId", "memberId"],
	indices = [Index("messageId"), Index("memberId")],
	foreignKeys =
	[
		ForeignKey(
			entity = MessageEntity::class,
			parentColumns = ["id"],
			childColumns = ["messageId"],
			onDelete = ForeignKey.CASCADE
		),
		ForeignKey(
			entity = MemberEntity::class,
			parentColumns = ["id"],
			childColumns = ["memberId"],
			onDelete = ForeignKey.CASCADE
		)
	]
)
data class MessageMentionCrossRef(val messageId: String, val memberId: String)

@Entity(
	tableName = "message_author_relation",
	primaryKeys = ["messageId", "memberId"],
	indices = [Index(value = ["messageId", "memberId"], unique = true)],
	foreignKeys =
	[
		ForeignKey(
			entity = MessageEntity::class,
			parentColumns = ["id"],
			childColumns = ["messageId"],
			onDelete = ForeignKey.CASCADE
		),
		ForeignKey(
			entity = MemberEntity::class,
			parentColumns = ["id"],
			childColumns = ["memberId"],
			onDelete = ForeignKey.CASCADE
		)
	]
)
data class MessageAuthorCrossRef(val messageId: String, val memberId: String)

@Entity(
	tableName = "parent_message_relation",
	primaryKeys = ["messageId", "parentMessageId"],
	indices = [Index(value = ["messageId", "parentMessageId"], unique = true)],
	foreignKeys =
	[
		ForeignKey(
			entity = MessageEntity::class,
			parentColumns = ["id"],
			childColumns = ["messageId"],
			onDelete = ForeignKey.CASCADE
		),
		ForeignKey(
			entity = MessageEntity::class,
			parentColumns = ["id"],
			childColumns = ["parentMessageId"],
			onDelete = ForeignKey.NO_ACTION
		)
	]
)
data class ParentMessageCrossRef(val messageId: String, val parentMessageId: String)
