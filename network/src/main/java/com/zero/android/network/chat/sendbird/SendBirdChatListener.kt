package com.zero.android.network.chat.sendbird

import com.sendbird.android.BaseChannel
import com.sendbird.android.BaseMessage
import com.sendbird.android.GroupChannel
import com.sendbird.android.OpenChannel
import com.sendbird.android.PollUpdateEvent
import com.sendbird.android.PollVoteEvent
import com.sendbird.android.ReactionEvent
import com.sendbird.android.SendBird
import com.sendbird.android.ThreadInfoUpdateEvent
import com.sendbird.android.User
import com.zero.android.network.chat.ChatListener
import com.zero.android.network.chat.conversion.toApi
import com.zero.android.network.chat.conversion.toType
import com.zero.android.network.model.events.ApiMessageReactionEvent

class SendBirdChatListener(private val listener: ChatListener) : SendBird.ChannelHandler() {
	override fun onMessageReceived(p0: BaseChannel, p1: BaseMessage) {
		listener.onMessageReceived(p0.toApi(), p1.toApi())
	}

	override fun onMessageDeleted(channel: BaseChannel, msgId: Long) {
		listener.onMessageDeleted(channel.toApi(), msgId.toString())
	}

	override fun onMessageUpdated(channel: BaseChannel, message: BaseMessage) {
		listener.onMessageUpdated(channel.toApi(), message.toApi())
	}

	override fun onMentionReceived(channel: BaseChannel, message: BaseMessage) {
		listener.onMentionReceived(channel.toApi(), message.toApi())
	}

	override fun onChannelChanged(channel: BaseChannel) {
		listener.onChannelChanged(channel.toApi())
	}

	override fun onChannelDeleted(channelUrl: String, channelType: BaseChannel.ChannelType) {
		listener.onChannelDeleted(channelUrl, channelType.toType())
	}

	override fun onReactionUpdated(channel: BaseChannel, reactionEvent: ReactionEvent) {
		listener.onReactionUpdated(
			channel.toApi(),
			ApiMessageReactionEvent(
				reaction = reactionEvent.toApi(),
				action = reactionEvent.operation.toType()
			)
		)
	}

	override fun onReadReceiptUpdated(channel: GroupChannel) {
		listener.onReadReceiptUpdated(channel.toApi())
	}

	override fun onDeliveryReceiptUpdated(channel: GroupChannel) {
		listener.onDeliveryReceiptUpdated(channel.toApi())
	}

	override fun onTypingStatusUpdated(channel: GroupChannel) {
		listener.onTypingStatusUpdated(channel.toApi())
	}

	override fun onUserReceivedInvitation(
		channel: GroupChannel,
		inviter: User,
		invitees: MutableList<User>?
	) {
		listener.onUserReceivedInvitation(
			channel.toApi(),
			inviter.toApi(),
			invitees?.map { it.toApi() }
		)
	}

	override fun onUserJoined(channel: GroupChannel, user: User) {
		listener.onUserJoined(channel.toApi(), user.toApi())
	}

	override fun onUserDeclinedInvitation(channel: GroupChannel, inviter: User, invitee: User) {
		listener.onUserDeclinedInvitation(channel.toApi(), inviter.toApi(), invitee.toApi())
	}

	override fun onUserLeft(channel: GroupChannel, user: User) {
		listener.onUserLeft(channel.toApi(), user.toApi())
	}

	override fun onUserEntered(channel: OpenChannel, user: User) {
		listener.onUserEntered(channel.toApi(), user.toApi())
	}

	override fun onUserExited(channel: OpenChannel, user: User) {
		listener.onUserExited(channel.toApi(), user.toApi())
	}

	override fun onUserMuted(channel: BaseChannel, user: User) {
		listener.onUserMuted(channel.toApi(), user.toApi())
	}

	override fun onUserUnmuted(channel: BaseChannel, user: User) {
		listener.onUserUnmuted(channel.toApi(), user.toApi())
	}

	override fun onUserBanned(channel: BaseChannel, user: User) {
		listener.onUserBanned(channel.toApi(), user.toApi())
	}

	override fun onUserUnbanned(channel: BaseChannel, user: User) {
		listener.onUserUnbanned(channel.toApi(), user.toApi())
	}

	override fun onChannelFrozen(channel: BaseChannel) {
		listener.onChannelFrozen(channel.toApi())
	}

	override fun onChannelUnfrozen(channel: BaseChannel) {
		listener.onChannelUnfrozen(channel.toApi())
	}

	override fun onMetaDataCreated(channel: BaseChannel, metaDataMap: Map<String, String?>?) {
		listener.onMetadataCreated(channel.toApi(), metaDataMap)
	}

	override fun onMetaDataUpdated(channel: BaseChannel, metaDataMap: Map<String, String?>?) {
		listener.onMetadataUpdated(channel.toApi(), metaDataMap)
	}

	override fun onMetaDataDeleted(channel: BaseChannel, keys: MutableList<String>) {
		listener.onMetadataDeleted(channel.toApi(), keys)
	}

	override fun onMetaCountersCreated(
		channel: BaseChannel,
		metaCounterMap: MutableMap<String, Int?>?
	) {
		listener.onMetaCountersCreated(channel.toApi(), metaCounterMap)
	}

	override fun onMetaCountersUpdated(
		channel: BaseChannel,
		metaCounterMap: MutableMap<String, Int?>?
	) {
		listener.onMetaCountersUpdated(channel.toApi(), metaCounterMap)
	}

	override fun onMetaCountersDeleted(channel: BaseChannel, keys: MutableList<String>) {
		listener.onMetaCountersDeleted(channel.toApi(), keys)
	}

	override fun onChannelHidden(channel: GroupChannel) {
		listener.onChannelHidden(channel.toApi())
	}

	override fun onOperatorUpdated(channel: BaseChannel) {
		listener.onOperatorUpdated(channel.toApi())
	}

	override fun onThreadInfoUpdated(
		channel: BaseChannel,
		threadInfoUpdateEvent: ThreadInfoUpdateEvent?
	) {
		super.onThreadInfoUpdated(channel, threadInfoUpdateEvent)
	}

	override fun onChannelMemberCountChanged(groupChannels: MutableList<GroupChannel>) {
		listener.onChannelMemberCountChanged(groupChannels.map { it.toApi() })
	}

	override fun onChannelParticipantCountChanged(openChannels: MutableList<OpenChannel>) {
		listener.onChannelParticipantCountChanged(openChannels.map { it.toApi() })
	}

	override fun onPollUpdated(event: PollUpdateEvent) {
		super.onPollUpdated(event)
	}

	override fun onPollVoted(event: PollVoteEvent) {
		super.onPollVoted(event)
	}
}
