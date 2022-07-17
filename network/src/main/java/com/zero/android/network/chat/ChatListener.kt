package com.zero.android.network.chat

import com.zero.android.models.enums.ChannelType
import com.zero.android.network.model.ApiChannel
import com.zero.android.network.model.ApiGroupChannel
import com.zero.android.network.model.ApiMember
import com.zero.android.network.model.ApiMessage
import com.zero.android.network.model.events.ApiMessageReactionEvent

interface ChatListener {
	fun onMessageReceived(channel: ApiChannel, message: ApiMessage)

	fun onMentionReceived(channel: ApiChannel, message: ApiMessage) {}

	fun onMessageDeleted(channel: ApiChannel, msgId: String) {}

	fun onMessageUpdated(channel: ApiChannel, message: ApiMessage) {}

	fun onChannelChanged(channel: ApiChannel) {}

	fun onChannelDeleted(url: String, channelType: ChannelType) {}

	fun onReactionUpdated(channel: ApiChannel, event: ApiMessageReactionEvent) {}

	fun onReadReceiptUpdated(channel: ApiChannel) {}

	fun onDeliveryReceiptUpdated(channel: ApiChannel) {}

	fun onTypingStatusUpdated(channel: ApiChannel) {}

	fun onUserReceivedInvitation(
		channel: ApiChannel,
		inviter: ApiMember?,
		invitees: List<ApiMember?>?
	) {}

	fun onUserJoined(channel: ApiChannel, user: ApiMember?) {}

	fun onUserDeclinedInvitation(channel: ApiChannel, inviter: ApiMember?, invitee: ApiMember?) {}

	fun onUserLeft(channel: ApiChannel, user: ApiMember?) {}

	fun onUserEntered(channel: ApiGroupChannel, user: ApiMember?) {}

	fun onUserExited(channel: ApiGroupChannel, user: ApiMember?) {}

	fun onUserMuted(channel: ApiChannel, user: ApiMember?) {}

	fun onUserUnmuted(channel: ApiChannel, user: ApiMember?) {}

	fun onUserBanned(channel: ApiChannel, user: ApiMember?) {}

	fun onUserUnbanned(channel: ApiChannel, user: ApiMember?) {}

	fun onChannelFrozen(channel: ApiChannel) {}

	fun onChannelUnfrozen(channel: ApiChannel) {}

	fun onMetadataCreated(channel: ApiChannel, metadataMap: Map<String, String?>?) {}

	fun onMetadataUpdated(channel: ApiChannel, metadataMap: Map<String, String?>?) {}

	fun onMetadataDeleted(channel: ApiChannel, keys: List<String?>?) {}

	fun onMetaCountersCreated(channel: ApiChannel, metaCounterMap: Map<String, Int?>?) {}

	fun onMetaCountersUpdated(channel: ApiChannel, metaCounterMap: Map<String, Int?>?) {}

	fun onMetaCountersDeleted(channel: ApiChannel, keys: List<String>?) {}

	fun onChannelHidden(channel: ApiChannel) {}

	fun onOperatorUpdated(channel: ApiChannel) {}

	fun onChannelMemberCountChanged(ApiChannels: List<ApiChannel>?) {}

	fun onChannelParticipantCountChanged(openChannels: List<ApiGroupChannel>?) {}
}
