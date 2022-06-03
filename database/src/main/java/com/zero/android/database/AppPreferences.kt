package com.zero.android.database

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.zero.android.models.AuthCredentials
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Singleton
class AppPreferences(private val dataStore: DataStore<Preferences>) {

	companion object {
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
}
