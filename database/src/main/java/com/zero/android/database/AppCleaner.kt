package com.zero.android.database

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit

class AppCleaner
constructor(private val database: AppDatabase, private val dataStore: DataStore<Preferences>) {

	suspend fun clean() {
		database.clearAllTables()
		dataStore.edit { it.clear() }
	}
}
