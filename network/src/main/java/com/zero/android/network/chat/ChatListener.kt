package com.zero.android.network.chat

import com.zero.android.models.enums.ChannelType
import com.zero.android.network.model.*
import com.zero.android.network.model.events.ApiMessageReactionEvent

interface ChatListener {
	fun onMessageReceived(var1: ApiChannel, var2: ApiMessage)

	fun onMentionReceived(channel: ApiChannel, message: ApiMessage) {}

	fun onMessageDeleted(channel: ApiChannel, msgId: Long) {}

	fun onMessageUpdated(channel: ApiChannel, message: ApiMessage) {}

	fun onChannelChanged(channel: ApiChannel) {}

	fun onChannelDeleted(url: String, channelType: ChannelType) {}

	fun onReactionUpdated(channel: ApiChannel, event: ApiMessageReactionEvent) {}

	fun onReadReceiptUpdated(channel: ApiGroupChannel) {}

	fun onDeliveryReceiptUpdated(channel: ApiGroupChannel) {}

	fun onTypingStatusUpdated(channel: ApiGroupChannel) {}

	fun onUserReceivedInvitation(
		channel: ApiGroupChannel,
		inviter: ApiMember?,
		invitees: List<ApiMember?>?
	) {}

	fun onUserJoined(channel: ApiGroupChannel, user: ApiMember?) {}

	fun onUserDeclinedInvitation(
		channel: ApiGroupChannel,
		inviter: ApiMember?,
		invitee: ApiMember?
	) {}

	fun onUserLeft(channel: ApiGroupChannel, user: ApiMember?) {}

	fun onUserEntered(channel: ApiOpenChannel, user: ApiMember?) {}

	fun onUserExited(channel: ApiOpenChannel, user: ApiMember?) {}

	fun onUserMuted(channel: ApiChannel, user: ApiMember?) {}

	fun onUserUnmuted(channel: ApiChannel, user: ApiMember?) {}

	fun onUserBanned(channel: ApiChannel, user: ApiMember?) {}

	fun onUserUnbanned(channel: ApiChannel, user: ApiMember?) {}

	fun onChannelFrozen(channel: ApiChannel) {}

	fun onChannelUnfrozen(channel: ApiChannel) {}

	fun onMetaDataCreated(channel: ApiChannel, metaDataMap: Map<String, String?>?) {}

	fun onMetaDataUpdated(channel: ApiChannel, metaDataMap: Map<String, String?>?) {}

	fun onMetaDataDeleted(channel: ApiChannel, keys: List<String?>?) {}

	fun onMetaCountersCreated(channel: ApiChannel, metaCounterMap: Map<String, Int?>?) {}

	fun onMetaCountersUpdated(channel: ApiChannel, metaCounterMap: Map<String, Int?>?) {}

	fun onMetaCountersDeleted(channel: ApiChannel, keys: List<String>?) {}

	fun onChannelHidden(channel: ApiGroupChannel) {}

	fun onOperatorUpdated(channel: ApiChannel) {}

	fun onChannelMemberCountChanged(ApiGroupChannels: List<ApiGroupChannel>?) {}

	fun onChannelParticipantCountChanged(openChannels: List<ApiOpenChannel>?) {}
}
