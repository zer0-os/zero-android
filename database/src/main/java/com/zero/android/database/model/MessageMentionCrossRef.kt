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
