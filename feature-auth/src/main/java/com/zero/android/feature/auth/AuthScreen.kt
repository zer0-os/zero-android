package com.zero.android.feature.auth

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.auth0.android.Auth0
import com.auth0.android.lock.AuthenticationCallback
import com.auth0.android.lock.Lock
import com.zero.android.common.extensions.OnEvent

@Composable
fun AuthRoute(viewModel: AuthViewModel = hiltViewModel()) {
	AuthScreen(authCallback = viewModel.authCallback)
}

@Composable
fun AuthScreen(
	lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
	authCallback: AuthenticationCallback
) {
	val context = LocalContext.current
	val lock =
		Lock.newBuilder(Auth0(context), authCallback)
			.allowedConnections(
				mutableListOf(
					"facebook",
					"twitter",
					"google-oauth2",
					"apple",
					"Username-Password-Authentication"
				)
			)
			.withScope("openid profile offline_access")
			.closable(false)
			.allowLogIn(true)
			.allowSignUp(true)
			.allowShowPassword(true)
			.allowForgotPassword(true)
			.loginAfterSignUp(true)
			.setMustAcceptTerms(false)
			.build(context)

	lifecycleOwner.OnEvent { _, event ->
		if (event == Lifecycle.Event.ON_DESTROY) {
			lock.onDestroy(context)
		}
	}

	context.startActivity(lock.newIntent(context))
}
