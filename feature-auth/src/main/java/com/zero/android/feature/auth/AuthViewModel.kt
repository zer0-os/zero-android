package com.zero.android.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.lock.AuthenticationCallback
import com.auth0.android.result.Credentials
import com.zero.android.data.repository.UserRepository
import com.zero.android.feature.auth.extensions.toAuthCredentials
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

	val authCallback =
		object : AuthenticationCallback() {
			override fun onAuthentication(credentials: Credentials) {
				onAuth(credentials)
			}

			override fun onError(error: AuthenticationException) {}
		}

	private fun onAuth(credentials: Credentials) =
		viewModelScope.launch { userRepository.login(credentials.toAuthCredentials()) }
}
