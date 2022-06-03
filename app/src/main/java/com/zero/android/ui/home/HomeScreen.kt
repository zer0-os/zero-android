package com.zero.android.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.zero.android.ui.HomeViewModel

@Composable
fun HomeRoute(
	windowSizeClass: WindowSizeClass,
	modifier: Modifier = Modifier,
	viewModel: HomeViewModel = hiltViewModel()
) {
	HomeScreen(windowSizeClass = windowSizeClass, modifier = modifier)
}

@Composable
fun HomeScreen(windowSizeClass: WindowSizeClass, modifier: Modifier = Modifier) {
	ConstraintLayout(modifier = Modifier.fillMaxSize()) {
		val (appBar, content, bottomBar) = createRefs()

		HomeContent(
			modifier =
			Modifier.constrainAs(content) {
				start.linkTo(parent.start)
				end.linkTo(parent.end)
				top.linkTo(appBar.bottom)
				bottom.linkTo(bottomBar.top)
			}
		)
	}
}
