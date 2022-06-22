package com.zero.android.feature.people

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MembersRoute(viewModel: MembersViewModel = hiltViewModel()) {
    MembersScreen()
}

@Composable
fun MembersScreen() {
    Column { Text(text = "Members Screen") }
}
