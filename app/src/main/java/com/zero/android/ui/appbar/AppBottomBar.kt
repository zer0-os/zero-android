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
import com.zero.android.feature.people.navigation.MembersDestination
import com.zero.android.ui.extensions.Preview
import com.zero.android.ui.theme.AppTheme

val HOME_DESTINATIONS =
	listOf(
		AppBarItem(
			ChannelsDestination,
			R.drawable.ic_channel_selected,
			R.drawable.ic_channel_unselected
		),
		AppBarItem(MembersDestination, R.drawable.ic_people, R.drawable.ic_people),
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
	Surface(color = MaterialTheme.colorScheme.surface) {
		NavigationBar(
			modifier =
			modifier.windowInsetsPadding(
				WindowInsets.safeDrawing.only(
					WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom
				)
			),
			tonalElevation = 0.dp
		) {
			HOME_DESTINATIONS.forEach { item ->
				val selected = currentDestination?.route == item.destination.route
				NavigationBarItem(
					selected = selected,
					onClick = { onNavigateToHomeDestination(item.destination) },
					icon = {
						val showBadgeCount = true
						if (showBadgeCount) {
							BadgedBox(
								badge = {
									Badge(
										containerColor = MaterialTheme.colorScheme.primary,
										contentColor = AppTheme.colors.colorTextPrimary
									) { Text("01") }
								}
							) { BottomBarIcon(isSelected = selected, item = item) }
						} else {
							BottomBarIcon(isSelected = selected, item = item)
						}
					}
				)
			}
		}
	}
}

@Composable
fun BottomBarIcon(isSelected: Boolean, item: AppBarItem) {
	Icon(
		painter =
		if (isSelected) painterResource(item.selectedIcon)
		else painterResource(item.unselectedIcon),
		contentDescription = null
	)
}

@Preview
@Composable
fun AppBottomBarPreview() = Preview { AppBottomBar(currentDestination = FeedDestination) {} }
