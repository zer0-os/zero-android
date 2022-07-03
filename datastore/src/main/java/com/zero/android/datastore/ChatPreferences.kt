package com.zero.android.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Singleton

@Singleton
class ChatPreferences(private val dataStore: DataStore<Preferences>) {

	companion object {
		private val CHAT_TOKEN = stringPreferencesKey("CHAT_TOKEN")
	}

	suspend fun chatToken() =
		dataStore.data.map { preferences -> preferences[CHAT_TOKEN] }.firstOrNull()

	suspend fun setChatToken(chatToken: String) {
		dataStore.edit { preferences -> preferences[CHAT_TOKEN] = chatToken }
	}
}
