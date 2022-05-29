package com.zero.android.feature.auth

import androidx.lifecycle.ViewModel
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.lock.AuthenticationCallback
import com.auth0.android.result.Credentials
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {

	val authCallback =
		object : AuthenticationCallback() {
			override fun onAuthentication(credentials: Credentials) {}

			override fun onError(error: AuthenticationException) {}
		}
}
