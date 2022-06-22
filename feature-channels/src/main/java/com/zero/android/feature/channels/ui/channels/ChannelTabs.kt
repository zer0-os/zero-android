package com.zero.android.feature.channels.ui.channels

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.zero.android.models.fake.ChannelTab
import com.zero.android.ui.theme.AppTheme
import com.zero.android.ui.theme.Typography
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ChannelTabLayout(
	pagerState: PagerState,
	coroutineScope: CoroutineScope,
	tabs: List<ChannelTab>
) {
	val tabIndex = pagerState.currentPage

	ScrollableTabRow(
		selectedTabIndex = tabIndex,
		edgePadding = 0.dp,
		modifier = Modifier.fillMaxWidth().wrapContentHeight(),
		indicator = { tabPositions ->
			Box(
				modifier =
				Modifier.tabIndicatorOffset(tabPositions[tabIndex])
					.height(2.dp)
					.background(color = AppTheme.colors.glow)
			)
		},
		containerColor = AppTheme.colors.surfaceInverse
	) {
		tabs.forEachIndexed { index, item ->
			val isSelectedTab = tabIndex == index
			Tab(
				selected = isSelectedTab,
				onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
				text = { TabItemWithBadge(item, isSelectedTab) }
			)
		}
	}
}

@Composable
fun TabItemWithBadge(channelTab: ChannelTab, isSelectedTab: Boolean) {
	Row(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
		if (isSelectedTab) {
			Text(
				text = channelTab.name.ifEmpty { "Private" },
				color = AppTheme.colors.colorTextPrimary,
				modifier = Modifier.align(Alignment.CenterVertically),
				fontWeight = FontWeight.Medium,
				style =
				TextStyle(
					shadow =
					Shadow(
						color = AppTheme.colors.glow,
						offset = Offset(2f, 2f),
						blurRadius = 50f
					),
					fontWeight = FontWeight.Normal,
					fontSize = 14.sp
				)
			)
		} else {
			Text(
				text = channelTab.name,
				color = AppTheme.colors.colorTextPrimary,
				modifier = Modifier.align(Alignment.CenterVertically),
				fontWeight = FontWeight.Medium,
				style = Typography.bodyMedium
			)
		}
		if (channelTab.unreadCount > 0) {
			Spacer(modifier = Modifier.padding(2.dp))
			Text(
				color = AppTheme.colors.surfaceInverse,
				text = channelTab.unreadCount.toString(),
				modifier =
				Modifier.background(color = AppTheme.colors.glow, shape = RoundedCornerShape(24.dp))
					.wrapContentHeight()
					.padding(6.dp, 2.dp, 6.dp, 2.dp),
				style = Typography.labelLarge
			)
		}
	}
}
