package com.zero.android.feature.account.ui.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ProfileRoute(viewModel: ProfileViewModel = hiltViewModel()) {
    ProfileScreen()
}

@Composable
fun ProfileScreen() {
    Column { Text(text = "Profile Screen") }
}
