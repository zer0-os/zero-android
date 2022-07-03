package com.zero.android.database

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.zero.android.database.base.BaseDatabaseTest
import com.zero.android.database.model.MemberEntity
import com.zero.android.database.model.MessageEntity
import com.zero.android.database.model.MessageWithRefs
import com.zero.android.models.enums.MessageMentionType
import com.zero.android.models.enums.MessageStatus
import com.zero.android.models.enums.MessageType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class MessageEntityTest : BaseDatabaseTest() {

	@Test
	fun insert() = runTest {
		val message =
			MessageEntity(
				id = "messageId",
				channelId = "channelId",
				type = MessageType.TEXT,
				mentionType = MessageMentionType.UNKNOWN,
				createdAt = 0,
				updatedAt = 0,
				status = MessageStatus.PENDING
			)
		val parentMessage =
			MessageEntity(
				id = "parentMessage",
				channelId = "channelId",
				type = MessageType.TEXT,
				mentionType = MessageMentionType.UNKNOWN,
				createdAt = 0,
				updatedAt = 0,
				status = MessageStatus.PENDING
			)

		db.messageDao()
			.insert(
				MessageWithRefs(
					message = message,
					author = MemberEntity(id = "authorId"),
					parentMessage = parentMessage,
					parentMessageAuthor = MemberEntity(id = "parentAuthorId"),
					mentions = listOf(MemberEntity(id = "memberOne"), MemberEntity(id = "memberTwo"))
				)
			)
		db.messageDao().getById("messageId").firstOrNull()
	}
}
