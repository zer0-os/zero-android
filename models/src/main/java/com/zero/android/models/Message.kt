package com.zero.android.models

import com.zero.android.models.enums.MessageMentionType
import com.zero.android.models.enums.MessageStatus
import com.zero.android.models.enums.MessageType
import java.io.File

internal interface BaseMessage {
    val channelUrl: String?
    val author: Member
    val type: MessageType
    val mentionType: MessageMentionType
    val message: String?
    val createdAt: Long
    val updatedAt: Long
    val status: MessageStatus
    val data: String?
    val isMuted: Boolean
    val fileName: String?
    val fileThumbnails: List<FileThumbnail>?
    val fileMimeType: String?
}

data class Message(
    val id: String,
    override val channelUrl: String?,
    override val author: Member,
    val mentions: List<Member> = emptyList(),
    override val type: MessageType,
    override val mentionType: MessageMentionType,
    override val message: String? = null,
    override val createdAt: Long,
    override val updatedAt: Long,
    override val status: MessageStatus,
    override val data: String? = null,
    val parentMessage: Message? = null,
    override val isMuted: Boolean = false,
    val fileUrl: String? = null,
    override val fileName: String? = null,
    override val fileThumbnails: List<FileThumbnail>? = null,
    override val fileMimeType: String? = null,
    val reactions: List<MessageReaction> = emptyList()
) : BaseMessage

data class DraftMessage(
    override val channelUrl: String?,
    override val author: Member,
    val mentions: List<String> = emptyList(),
    override val type: MessageType,
    override val mentionType: MessageMentionType,
    override val message: String? = null,
    override val createdAt: Long,
    override val updatedAt: Long,
    override val status: MessageStatus,
    override val data: String? = null,
    var parentMessageId: String? = null,
    override val isMuted: Boolean = false,
    val file: File? = null,
    val fileUrl: String? = null,
    override val fileName: String? = null,
    override val fileThumbnails: List<FileThumbnail>? = null,
    override val fileMimeType: String? = null
) : BaseMessage

data class FileThumbnail(
    var maxWidth: Int = 0,
    val maxHeight: Int = 0,
    val realWidth: Int = 0,
    val realHeight: Int = 0,
    val url: String? = null
)
