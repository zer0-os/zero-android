package com.zero.android.ui.appbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zero.android.common.R
import com.zero.android.common.navigation.NavDestination
import com.zero.android.feature.account.navigation.NotificationsDestination
import com.zero.android.feature.channels.navigation.ChannelsDestination
import com.zero.android.feature.feed.navigation.FeedDestination
import com.zero.android.feature.messages.navigation.MessagesDestination
import com.zero.android.feature.people.navigation.MembersDestination
import com.zero.android.ui.components.CountBadge
import com.zero.android.ui.extensions.Preview
import com.zero.android.ui.theme.AppTheme

val HOME_DESTINATIONS =
    listOf(
        AppBarItem(
            ChannelsDestination,
            R.drawable.ic_channel,
            R.drawable.ic_channel
        ),
        AppBarItem(
            MembersDestination,
            R.drawable.ic_people_selected,
            R.drawable.ic_people_unselected
        ),
        AppBarItem(FeedDestination, R.drawable.ic_feeds, R.drawable.ic_feeds),
        AppBarItem(
            NotificationsDestination,
            R.drawable.ic_notification_selected,
            R.drawable.ic_notification_unselected
        ),
        AppBarItem(
            MessagesDestination,
            R.drawable.ic_direct_chat_selected,
            R.drawable.ic_direct_chat_unselected
        )
    )

@Composable
fun AppBottomBar(
    modifier: Modifier = Modifier,
    currentDestination: NavDestination?,
    onNavigateToHomeDestination: (NavDestination) -> Unit
) {
    Column {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors
                        = listOf(
                            AppTheme.colors.surfaceInverse,
                            AppTheme.colors.glow,
                            AppTheme.colors.surfaceInverse,
                        )
                    )
                )
        )
        NavigationBar(
            modifier =
            modifier.windowInsetsPadding(
                WindowInsets.safeDrawing.only(
                    WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom
                )
            ),
            containerColor = AppTheme.colors.surfaceInverse,
            tonalElevation = 0.dp
        ) {
            HOME_DESTINATIONS.forEach { item ->
                val selected = currentDestination?.route == item.destination.route
                BottomNavigationItem(
                    selected = selected,
                    onClick = { onNavigateToHomeDestination(item.destination) },
                    icon = {
                        val showBadgeCount = false
                        if (showBadgeCount) {
                            BadgedBox(badge = { CountBadge(count = 1) }) {
                                BottomBarIcon(isSelected = selected, item = item)
                            }
                        } else {
                            BottomBarIcon(isSelected = selected, item = item)
                        }
                    },
                    alwaysShowLabel = false,
                    selectedContentColor = AppTheme.colors.glow,
                    unselectedContentColor = AppTheme.colors.surface
                )
            }
        }
    }
}

@Composable
fun BottomBarIcon(isSelected: Boolean, item: AppBarItem) {
    if (isSelected) {
        Icon(
            modifier =
            if (item.destination is FeedDestination) Modifier.size(32.dp) else Modifier.size(28.dp),
            painter = painterResource(item.selectedIcon),
            contentDescription = null,
        )
    } else {
        Icon(
            modifier =
            if (item.destination is FeedDestination) Modifier.size(36.dp) else Modifier.size(24.dp),
            painter = painterResource(item.unselectedIcon),
            contentDescription = null
        )
    }
}

@Preview
@Composable
fun AppBottomBarPreview() = Preview { AppBottomBar(currentDestination = FeedDestination) {} }
