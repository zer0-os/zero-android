package com.zero.android.database

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.zero.android.models.AuthCredentials
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Singleton
class AppPreferences(private val dataStore: DataStore<Preferences>) {

	companion object {
		private val USER_ID = stringPreferencesKey("USER_ID")
		private val CHAT_TOKEN = stringPreferencesKey("CHAT_TOKEN")
		private val AUTH_CREDENTIALS = stringPreferencesKey("AUTH_CREDENTIALS")
	}

	suspend fun token() = authCredentials()?.accessToken

	suspend fun authCredentials(): AuthCredentials? {
		val json: String =
			dataStore.data.map { preferences -> preferences[AUTH_CREDENTIALS] }.firstOrNull()
				?: return null
		return Json.decodeFromString<AuthCredentials>(json)
	}

	suspend fun setAuthCredentials(credentials: AuthCredentials) {
		dataStore.edit { preferences ->
			preferences[AUTH_CREDENTIALS] = Json.encodeToString(credentials)
		}
	}

	suspend fun userId() = dataStore.data.map { preferences -> preferences[USER_ID] }.first()!!

	suspend fun setUserId(id: String) {
		dataStore.edit { preferences -> preferences[USER_ID] = id }
	}

	suspend fun chatToken() = dataStore.data.map { preferences -> preferences[USER_ID] }.firstOrNull()

	suspend fun setChatToken(chatToken: String) {
		dataStore.edit { preferences -> preferences[CHAT_TOKEN] = chatToken }
	}
}
