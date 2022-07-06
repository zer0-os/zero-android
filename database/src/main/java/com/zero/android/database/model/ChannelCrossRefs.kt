package com.zero.android.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
	tableName = "channel_members_relation",
	primaryKeys = ["channelId", "memberId"],
	indices = [Index("channelId"), Index("memberId")],
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
			childColumns = ["memberId"],
			onDelete = ForeignKey.NO_ACTION
		)
	]
)
data class ChannelMembersCrossRef(val channelId: String, val memberId: String)

@Entity(
	tableName = "channel_operators_relation",
	primaryKeys = ["channelId", "memberId"],
	indices = [Index("channelId"), Index("memberId")],
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
			childColumns = ["memberId"],
			onDelete = ForeignKey.NO_ACTION
		)
	]
)
data class ChannelOperatorsCrossRef(val channelId: String, val memberId: String)
