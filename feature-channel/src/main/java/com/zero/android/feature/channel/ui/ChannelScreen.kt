package com.zero.android.feature.channel.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.zero.android.feature.channel.R
import com.zero.android.feature.network.drawer.misc.NetworkWorld
import com.zero.android.feature.network.drawer.ui.NetworkDrawer
import kotlinx.coroutines.launch

@Composable
fun ChannelRoute(
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier,
    viewModel: ChannelViewModel = hiltViewModel()
) {
    ChannelScreen(windowSizeClass = windowSizeClass, modifier = modifier, viewModel = viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
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
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val coroutineScope = rememberCoroutineScope()
        val (appBar, content, bottomBarDivider, bottomBar) = createRefs()

        ChannelAppBar(
            modifier = Modifier.constrainAs(appBar) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(content.top)
            },
            openDrawer = {
                coroutineScope.launch { drawerState.open() }
            },
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
        Image(
            painter = painterResource(R.drawable.img_bottom_bar_divider),
            contentDescription = stringResource(R.string.cd_channel_bottom_bar_divider),
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(bottomBarDivider) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
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
        NetworkDrawer(
            windowSizeClass = windowSizeClass,
            modifier = Modifier.fillMaxSize(),
            drawerState = drawerState,
            coroutineScope = coroutineScope
        )
    }
}
