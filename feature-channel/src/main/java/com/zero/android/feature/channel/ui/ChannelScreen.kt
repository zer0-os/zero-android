package com.zero.android.feature.channel.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.zero.android.feature.network.drawer.misc.NetworkWorld

@Composable
fun ChannelRoute(
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier,
    viewModel: ChannelViewModel = hiltViewModel()
) {
    ChannelScreen(windowSizeClass = windowSizeClass, modifier = modifier, viewModel = viewModel)
}

@Composable
fun ChannelScreen(
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier,
    viewModel: ChannelViewModel,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (appBar, content, bottomBar) = createRefs()

        ChannelAppBar(
            modifier = Modifier.constrainAs(appBar) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(content.top)
            },
            openDrawer = { /*TODO*/ },
            networkWorld = NetworkWorld(),
            onProfileClick = { /*TODO*/ }) {
        }
        ChannelContent(
            modifier = Modifier.constrainAs(content) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(appBar.bottom)
                bottom.linkTo(bottomBar.top)
            }
        )
        ChannelBottomBar(
            modifier = Modifier.constrainAs(bottomBar) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(content.bottom)
                bottom.linkTo(parent.bottom)
            },
            onNavigateToTopLevelDestination = {},
            currentDestination = null
        )
    }
}
