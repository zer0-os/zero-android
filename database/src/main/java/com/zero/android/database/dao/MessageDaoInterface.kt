package com.zero.android.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.zero.android.database.model.MessageEntity
import com.zero.android.database.model.MessageMentionCrossRef
import com.zero.android.database.model.MessageWithRefs
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MessageDaoInterface {

	@Transaction
	@Query("SELECT * FROM messages WHERE id = :id")
	abstract fun getById(id: String): Flow<MessageWithRefs>

	@Transaction
	@Query("SELECT * FROM messages WHERE channelId = :channelId")
	abstract fun getByChannel(channelId: String): Flow<MessageWithRefs>

	@Transaction
	internal open suspend fun insert(memberDao: MemberDao, vararg data: MessageWithRefs) {
		for (item in data) {
			val members = mutableListOf(item.author)
			item.mentions?.let { members.addAll(it) }
			item.parentMessageAuthor?.let { members.add(it) }
			memberDao.insert(members = members.toTypedArray())

			item.parentMessage?.let { insert(it) }
			insert(item.message)

			item.mentions
				?.map { MessageMentionCrossRef(messageId = item.message.id, memberId = it.id) }
				?.let { insert(*it.toTypedArray()) }
		}
	}

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	protected abstract suspend fun insert(vararg messages: MessageEntity)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	protected abstract suspend fun insert(vararg refs: MessageMentionCrossRef)

	@Delete abstract suspend fun delete(message: MessageEntity)
}
