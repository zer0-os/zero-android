package com.zero.android.feature.channel.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.zero.android.feature.channel.misc.CType
import com.zero.android.ui.theme.ZeroExtendedTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ChannelContent(
    modifier: Modifier = Modifier,
    cTypes: List<CType> = emptyList()
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    //TabView
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        modifier = Modifier.padding(10.dp),
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        }
    ) {
        cTypes.forEachIndexed { index, cType ->
            val showBadgeCount = cType.count > 0
            if (showBadgeCount) {
                BadgedBox(badge = {
                    Badge(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = ZeroExtendedTheme.colors.colorTextPrimary
                    ) {
                        Text(cType.count.toString())
                    }
                }) {
                    CTypeTab(isSelected = pagerState.currentPage == index, text = cType.title) {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                }
            } else {
                CTypeTab(isSelected = pagerState.currentPage == index, text = cType.title) {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            }
        }
    }
    //Viewpager
    HorizontalPager(
        count = cTypes.size,
        state = pagerState,
    ) { page ->
        // TODO: page content
    }
}

@Composable
fun CTypeTab(
    isSelected: Boolean,
    text: String,
    onClick: () -> Unit
) {
    Tab(
        selected = isSelected,
        onClick = onClick,
        text = { Text(text) }
    )
}

@Preview
@Composable
fun ChannelContentPreview() {

}
