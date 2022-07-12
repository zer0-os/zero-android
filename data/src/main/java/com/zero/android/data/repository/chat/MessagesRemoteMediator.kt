package com.zero.android.data.repository.chat

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.zero.android.data.conversion.toEntity
import com.zero.android.database.dao.MessageDao
import com.zero.android.models.Channel
import com.zero.android.models.Message
import com.zero.android.network.service.ChatService
import kotlinx.coroutines.flow.firstOrNull
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class MessagesRemoteMediator(
	private val chatService: ChatService,
	private val messageDao: MessageDao
) : RemoteMediator<Int, Message>() {

	private lateinit var channel: Channel

	override suspend fun load(loadType: LoadType, state: PagingState<Int, Message>): MediatorResult {
		return try {
			// The network load method takes an optional after=<user.id>
			// parameter. For every page after the first, pass the last user
			// ID to let it continue from where it left off. For REFRESH,
			// pass null to load the first page.
			val loadKey =
				when (loadType) {
					LoadType.REFRESH -> null
					// In this example, you never need to prepend, since REFRESH
					// will always load the first page in the list. Immediately
					// return, reporting end of pagination.
					LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
					LoadType.APPEND -> {
						val lastItem =
							state.lastItemOrNull()
								?: return MediatorResult.Success(endOfPaginationReached = true)

						// You must explicitly check if the last item is null when
						// appending, since passing null to networkService is only
						// valid for initial load. If lastItem is null it means no
						// items were loaded after the initial REFRESH and there are
						// no more items to load.
						lastItem.id
					}
				}

			// Suspending network load via Retrofit. This doesn't need to be
			// wrapped in a withContext(Dispatcher.IO) { ... } block since
			// Retrofit's Coroutine CallAdapter dispatches on a worker
			// thread.
			val response =
				loadKey?.let {
					chatService.getMessages(channel = channel, lastMessageId = loadKey).firstOrNull()
				}
					?: chatService.getMessages(channel = channel).firstOrNull()

			response?.map { it.toEntity() }?.let { messageDao.upsert(*it.toTypedArray()) }

			MediatorResult.Success(endOfPaginationReached = response.nextKey == null)
		} catch (e: IOException) {
			MediatorResult.Error(e)
		} catch (e: HttpException) {
			MediatorResult.Error(e)
		}
	}
}
