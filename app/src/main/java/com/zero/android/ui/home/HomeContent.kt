package com.zero.android.ui.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.zero.android.extensions.pagerTabIndicatorOffset
import com.zero.android.ui.extensions.Preview
import com.zero.android.ui.theme.ZeroExtendedTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun HomeContent(modifier: Modifier = Modifier, homeTabs: List<HomeTab> = emptyList()) {
	val coroutineScope = rememberCoroutineScope()
	val pagerState = rememberPagerState()

	TabRow(
		selectedTabIndex = pagerState.currentPage,
		modifier = Modifier.padding(10.dp),
		indicator = { tabPositions ->
			TabRowDefaults.Indicator(Modifier.pagerTabIndicatorOffset(pagerState, tabPositions))
		}
	) {
		homeTabs.forEachIndexed { index, tab ->
			val showBadgeCount = tab.count > 0
			if (showBadgeCount) {
				BadgedBox(
					badge = {
						Badge(
							containerColor = MaterialTheme.colorScheme.primary,
							contentColor = ZeroExtendedTheme.colors.colorTextPrimary
						) {
							Text(tab.count.toString())
						}
					}
				) {
					CTypeTab(isSelected = pagerState.currentPage == index, text = tab.title) {
						coroutineScope.launch { pagerState.animateScrollToPage(index) }
					}
				}
			} else {
				CTypeTab(isSelected = pagerState.currentPage == index, text = tab.title) {
					coroutineScope.launch { pagerState.animateScrollToPage(index) }
				}
			}
		}
	}
	// Viewpager
	HorizontalPager(count = homeTabs.size, state = pagerState) { page ->
		// TODO: page content
	}
}

@Composable
fun CTypeTab(isSelected: Boolean, text: String, onClick: () -> Unit) {
	Tab(selected = isSelected, onClick = onClick, text = { Text(text) })
}

@Preview @Composable
fun HomeContentPreview() = Preview { HomeContent() }
