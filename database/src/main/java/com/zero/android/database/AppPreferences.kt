package com.zero.android.database

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Singleton

@Singleton
class AppPreferences(private val dataStore: DataStore<Preferences>) {

	companion object {
		private val TOKEN = stringPreferencesKey("token")
	}

	suspend fun token() = dataStore.data.map { preferences -> preferences[TOKEN] }.firstOrNull()

	suspend fun setToken(token: String) = dataStore.edit { preferences -> preferences[TOKEN] = token }
}
