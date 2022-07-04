package com.zero.android.database.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class DirectChannelWithRefs(
	@Embedded val channel: ChannelEntity,
	@Relation(parentColumn = "id", entityColumn = "channelId")
	val lastMessage: MessageWithRefs? = null,
	@Relation(
		parentColumn = "id",
		entityColumn = "id",
		associateBy =
		Junction(
			value = ChannelMembersCrossRef::class,
			parentColumn = "channelId",
			entityColumn = "memberId"
		)
	)
	val members: List<MemberEntity>
)

data class GroupChannelWithRefs(
	@Embedded val channel: ChannelEntity,
	@Relation(parentColumn = "id", entityColumn = "channelId") val lastMessage: MessageWithRefs?,
	@Relation(
		parentColumn = "id",
		entityColumn = "id",
		associateBy =
		Junction(
			value = ChannelAuthorCrossRef::class,
			parentColumn = "channelId",
			entityColumn = "memberId"
		)
	)
	val createdBy: MemberEntity? = null,
	@Relation(
		parentColumn = "id",
		entityColumn = "id",
		associateBy =
		Junction(
			value = ChannelMembersCrossRef::class,
			parentColumn = "channelId",
			entityColumn = "memberId"
		)
	)
	val members: List<MemberEntity>,
	@Relation(
		parentColumn = "id",
		entityColumn = "id",
		associateBy =
		Junction(
			value = ChannelOperatorsCrossRef::class,
			parentColumn = "channelId",
			entityColumn = "memberId"
		)
	)
	val operators: List<MemberEntity>
)

fun DirectChannelWithRefs.toModel() =
	channel.toDirectModel(
		members = members.map { it.toModel() },
		lastMessage = lastMessage?.toModel()
	)

fun GroupChannelWithRefs.toModel() =
	channel.toGroupModel(
		members = members.map { it.toModel() },
		operators = operators.map { it.toModel() },
		lastMessage = lastMessage?.toModel(),
		createdBy = createdBy?.toModel()
	)
