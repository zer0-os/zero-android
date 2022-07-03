package com.zero.android.data.delegates

import com.zero.android.datastore.AppPreferences
import javax.inject.Inject

class PreferencesImpl @Inject constructor(private val appPreferences: AppPreferences) :
	Preferences {

	override suspend fun userId() = appPreferences.userId()
}
