package com.zero.android.ui.appbar

import androidx.compose.foundation.layout.*
import androidx.compose.material.BadgedBox
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zero.android.common.R
import com.zero.android.common.navigation.NavDestination
import com.zero.android.feature.account.navigation.NotificationsDestination
import com.zero.android.feature.channels.navigation.ChannelsDestination
import com.zero.android.feature.channels.ui.directchannels.DirectChannelDestination
import com.zero.android.feature.feed.navigation.FeedDestination
import com.zero.android.feature.people.navigation.MembersDestination
import com.zero.android.ui.components.BottomBarDivider
import com.zero.android.ui.components.CountBadge
import com.zero.android.ui.extensions.Preview
import com.zero.android.ui.theme.AppTheme

val HOME_DESTINATIONS =
	listOf(
		AppBarItem(ChannelsDestination, R.drawable.ic_channel, R.drawable.ic_channel),
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
			DirectChannelDestination,
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
		BottomBarDivider()
		BottomNavigation(
			modifier =
			modifier
				.windowInsetsPadding(
					WindowInsets.safeDrawing.only(
						WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom
					)
				)
				.fillMaxWidth(),
			backgroundColor = AppTheme.colors.surfaceInverse,
			contentColor = AppTheme.colors.surface,
			elevation = 0.dp
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
	val iconId = if (isSelected) item.selectedIcon else item.unselectedIcon
	Icon(
		modifier =
		if (item.destination is FeedDestination) Modifier.size(32.dp) else Modifier.size(20.dp),
		painter = painterResource(iconId),
		contentDescription = null
	)
}

@Preview
@Composable
fun AppBottomBarPreview() = Preview { AppBottomBar(currentDestination = FeedDestination) {} }
