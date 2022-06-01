package com.zero.android.ui

import androidx.lifecycle.ViewModel
import com.zero.android.common.navigation.NavDestination
import com.zero.android.database.AppPreferences
import com.zero.android.feature.auth.navigation.AuthDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(private val preferences: AppPreferences) : ViewModel() {

	lateinit var startDestination: NavDestination

	init {
		checkAuthOnLaunch()
	}

	private fun checkAuthOnLaunch() {
		val isLoggedIn = runBlocking(Dispatchers.IO) { preferences.authCredentials() != null }
		startDestination = if (isLoggedIn) AuthDestination else AuthDestination
	}
}
