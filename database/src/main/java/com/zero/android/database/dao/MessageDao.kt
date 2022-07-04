package com.zero.android.database.dao

import com.zero.android.database.model.MessageEntity
import com.zero.android.database.model.MessageWithRefs
import javax.inject.Inject

class MessageDao
@Inject
constructor(private val messageDao: MessageDaoInterface, private val memberDao: MemberDao) {

	fun getById(id: String) = messageDao.getById(id)

	fun getByChannel(channelId: String) = messageDao.getByChannel(channelId)

	suspend fun insert(vararg data: MessageWithRefs) = messageDao.insert(memberDao, *data)

	suspend fun delete(message: MessageEntity) = messageDao.delete(message)
}
