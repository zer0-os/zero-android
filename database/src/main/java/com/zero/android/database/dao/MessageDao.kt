package com.zero.android.database.dao

import com.zero.android.database.model.MessageEntity
import com.zero.android.database.model.MessageWithRefs
import javax.inject.Inject

class MessageDao
@Inject
constructor(private val messageDao: MessageDaoInterface, private val memberDao: MemberDao) {

	fun get(id: String) = messageDao.get(id)

	fun getByChannel(channelId: String) = messageDao.getByChannel(channelId)

	suspend fun upsert(vararg data: MessageWithRefs) = messageDao.upsert(memberDao, *data)

	suspend fun delete(id: String) = messageDao.delete(id)

	suspend fun delete(message: MessageEntity) = messageDao.delete(message)
}
