package com.zero.android.feature.auth.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AuthRoute(
	windowSizeClass: WindowSizeClass,
	modifier: Modifier = Modifier,
	viewModel: AuthViewModel = hiltViewModel()
) {
	AuthScreen(windowSizeClass = windowSizeClass, modifier = modifier)
}

@Composable fun AuthScreen(windowSizeClass: WindowSizeClass, modifier: Modifier = Modifier) {}
