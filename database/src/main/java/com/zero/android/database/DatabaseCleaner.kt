package com.zero.android.database

import javax.inject.Inject

class DatabaseCleaner @Inject constructor(private val database: AppDatabase) {

	suspend fun clean() {
		database.clearAllTables()
	}
}
