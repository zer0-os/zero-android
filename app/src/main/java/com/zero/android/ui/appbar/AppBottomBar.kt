package com.zero.android.ui.appbar

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zero.android.common.R
import com.zero.android.common.navigation.NavDestination
import com.zero.android.feature.account.navigation.NotificationsDestination
import com.zero.android.feature.channels.navigation.ChannelsDestination
import com.zero.android.feature.feed.navigation.FeedDestination
import com.zero.android.feature.messages.navigation.MessagesDestination
import com.zero.android.navigation.HomeDestination
import com.zero.android.ui.extensions.Preview
import com.zero.android.ui.theme.ZeroExtendedTheme

val HOME_DESTINATIONS =
	listOf(
		AppBarNavigation(
			ChannelsDestination.route,
			R.drawable.ic_channel_selected,
			R.drawable.ic_channel_unselected
		),
		AppBarNavigation("{ROUTE_PEOPLE}", R.drawable.ic_people, R.drawable.ic_people),
		AppBarNavigation(FeedDestination.route, R.drawable.ic_feeds, R.drawable.ic_feeds),
		AppBarNavigation(
			NotificationsDestination.route,
			R.drawable.ic_notification_selected,
			R.drawable.ic_notification_unselected
		),
		AppBarNavigation(
			MessagesDestination.route,
			R.drawable.ic_direct_chat_selected,
			R.drawable.ic_direct_chat_unselected
		)
	)

@Composable
fun AppBottomBar(
	modifier: Modifier = Modifier,
	currentDestination: NavDestination?,
	onNavigateToTopLevelDestination: (AppBarNavigation) -> Unit
) {
	Surface(color = MaterialTheme.colorScheme.surface) {
		NavigationBar(
			modifier =
			Modifier.windowInsetsPadding(
				WindowInsets.safeDrawing.only(
					WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom
				)
			),
			tonalElevation = 0.dp
		) {
			HOME_DESTINATIONS.forEach { destination ->
				val selected = currentDestination?.route == destination.route
				NavigationBarItem(
					selected = selected,
					onClick = { onNavigateToTopLevelDestination(destination) },
					icon = {
						val showBadgeCount = true
						if (showBadgeCount) {
							BadgedBox(
								badge = {
									Badge(
										containerColor = MaterialTheme.colorScheme.primary,
										contentColor = ZeroExtendedTheme.colors.colorTextPrimary
									) { Text("01") }
								}
							) { BottomBarIcon(isSelected = selected, destination = destination) }
						} else {
							BottomBarIcon(isSelected = selected, destination = destination)
						}
					}
				)
			}
		}
	}
}

@Composable
fun BottomBarIcon(isSelected: Boolean, destination: AppBarNavigation) {
	Icon(
		painter =
		if (isSelected) {
			painterResource(destination.selectedIcon)
		} else {
			painterResource(destination.unselectedIcon)
		},
		contentDescription = null
	)
}

@Preview
@Composable
fun AppBottomBarPreview() = Preview { AppBottomBar(currentDestination = HomeDestination) {} }
