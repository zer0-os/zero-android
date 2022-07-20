package com.zero.android.database

import javax.inject.Inject

class DatabaseCleaner @Inject constructor(private val database: AppDatabase) {

	fun clean() {
		database.clearAllTables()
	}
}
