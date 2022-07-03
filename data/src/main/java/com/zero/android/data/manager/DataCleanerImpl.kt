package com.zero.android.data.manager

import com.zero.android.database.DatabaseCleaner
import com.zero.android.datastore.DatastoreCleaner
import javax.inject.Inject

class DataCleanerImpl
@Inject
constructor(private val database: DatabaseCleaner, private val datastore: DatastoreCleaner) :
	DataCleaner {

	override suspend fun clean() {
		database.clean()
		datastore.clean()
	}
}
