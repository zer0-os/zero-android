package com.zero.android.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import javax.inject.Inject

class DatastoreCleaner @Inject constructor(private val dataStore: DataStore<Preferences>) {

	suspend fun clean() {
		dataStore.edit { it.clear() }
	}
}
