package com.zero.android.database.dao

import com.zero.android.database.model.ChannelEntity
import com.zero.android.database.model.DirectChannelWithRefs
import com.zero.android.database.model.GroupChannelWithRefs
import javax.inject.Inject

class ChannelDao
@Inject
constructor(
	private val directChannelDao: DirectChannelDaoInterface,
	private val groupChannelDao: GroupChannelDaoInterface,
	private val memberDao: MemberDao,
	private val messageDao: MessageDao
) {

	fun getGroupChannels() = groupChannelDao.getAll()

	fun getDirectChannels() = directChannelDao.getAll()

	fun getGroupChannelById(id: String) = groupChannelDao.getById(id)

	fun getDirectChannelById(id: String) = directChannelDao.getById(id)

	suspend fun insert(vararg data: DirectChannelWithRefs) =
		directChannelDao.insert(messageDao, memberDao, *data)

	suspend fun insert(vararg data: GroupChannelWithRefs) =
		groupChannelDao.insert(messageDao, memberDao, *data)

	suspend fun update(vararg data: DirectChannelWithRefs) =
		directChannelDao.update(messageDao, *data)

	suspend fun update(vararg data: GroupChannelWithRefs) = groupChannelDao.update(messageDao, *data)

	suspend fun delete(entity: ChannelEntity) = groupChannelDao.delete(entity)
}
