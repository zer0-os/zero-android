package com.zero.android.network.model

import com.zero.android.models.ChannelCategory
import com.zero.android.models.enums.AccessType
import com.zero.android.models.enums.AlertType
import com.zero.android.models.enums.ChannelType
import com.zero.android.network.model.serializer.AccessTypeSerializer
import com.zero.android.network.model.serializer.AlertTypeSerializer
import com.zero.android.network.model.serializer.ChannelTypeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface ApiChannel {
    val id: String
    val members: List<ApiMember>
    val memberCount: Int
    val coverUrl: String?
    val createdAt: Long
    val isTemporary: Boolean
    val unreadMentionCount: Int
    val unreadMessageCount: Int
    val lastMessage: ApiMessage?
    val alerts: AlertType
    val accessCode: String?
}

@Serializable
data class ApiDirectChannel(
    override val id: String,
    override val members: List<ApiMember>,
    override val memberCount: Int,
    override val coverUrl: String? = null,
    override val lastMessage: ApiMessage? = null,
    override val createdAt: Long,
    override val isTemporary: Boolean = false,
    override val unreadMentionCount: Int = 0,
    override val unreadMessageCount: Int = 0,
    @Serializable(AlertTypeSerializer::class) override val alerts: AlertType = AlertType.ALL,
    override val accessCode: String? = null
) : ApiChannel

@Serializable
data class ApiGroupChannel(
    override val id: String,
    val networkId: String,
    val category: ChannelCategory? = null,
    val name: String,
    val operators: List<ApiMember>,
    val operatorCount: Int,
    override val members: List<ApiMember>,
    override val memberCount: Int,
    override val coverUrl: String? = null,
    override val lastMessage: ApiMessage? = null,
    override val createdAt: Long,
    @SerialName("data") val properties: ApiChannelProperties? = null,
    override val isTemporary: Boolean = false,
    val isSuper: Boolean = false,
    val isPublic: Boolean = false,
    val isDiscoverable: Boolean = false,
    val isVideoEnabled: Boolean = false,
    val createdBy: ApiMember? = null,
    @Serializable(AlertTypeSerializer::class) override val alerts: AlertType = AlertType.ALL,
    val messageLifeSeconds: Int = 0,
    override val accessCode: String? = null,
    @Serializable(ChannelTypeSerializer::class) val type: ChannelType = ChannelType.GROUP,
    @Serializable(AccessTypeSerializer::class) val accessType: AccessType = AccessType.PUBLIC,
    override val unreadMentionCount: Int = 0,
    override val unreadMessageCount: Int = 0
) : ApiChannel

@Serializable
data class ApiChannelProperties(
    val category: ChannelCategory? = null,
    val isAdminOnly: Boolean = false,
    @Serializable(AccessTypeSerializer::class) val groupChannelType: AccessType,
    val isVideoEnabled: Boolean = true,
    val telegramChatId: String? = null,
    val discordChatId: String? = null
)
