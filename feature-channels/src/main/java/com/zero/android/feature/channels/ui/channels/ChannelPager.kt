package com.zero.android.feature.channels.ui.channels

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.zero.android.common.R
import com.zero.android.common.extensions.toDate
import com.zero.android.common.extensions.toMessageDateFormat
import com.zero.android.models.Channel
import com.zero.android.models.GroupChannel
import com.zero.android.ui.theme.AppTheme
import com.zero.android.ui.theme.Typography

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ChannelPager(
    pagerState: PagerState,
    channelUiState: ChannelUiState,
    onClick: (Channel) -> Unit,
) {
    HorizontalPager(
        state = pagerState,
        count = channelUiState.channelCategories.size,
    ) { index ->
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn {
                channelUiState.groupMessages(
                    channelUiState.channelCategories[index].name
                )?.let {
                    items(it) { channel ->
                        ChannelsItemsList(channel)
                    }
                }
            }
        }
    }
}

@Composable
fun ChannelsItemsList(channel: Channel) {
    val groupChannel = channel as GroupChannel
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(12.dp)
    ) {
        val (image, textTop, textBottom, symbols, dateTime, unreadCount) = createRefs()

        Image(
            painter = rememberAsyncImagePainter(groupChannel.coverUrl),
            contentDescription = groupChannel.id,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(textTop.start)
                },
            contentScale = ContentScale.Fit,
        )
        Text(
            text = groupChannel.name,
            color = AppTheme.colors.colorTextPrimary,
            style = Typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.constrainAs(textTop) {
                top.linkTo(image.top)
                bottom.linkTo(textBottom.top)
                start.linkTo(image.end, margin = 12.dp)
                end.linkTo(symbols.start)
            }
        )
        Text(
            text = groupChannel.lastMessage?.message ?: "",
            color = AppTheme.colors.colorTextSecondary,
            style = Typography.bodyMedium,
            modifier = Modifier.constrainAs(textBottom) {
                top.linkTo(textTop.bottom, margin = 4.dp)
                start.linkTo(textTop.start)
                end.linkTo(unreadCount.start, margin = 12.dp)
                width = Dimension.fillToConstraints
            },
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Row(modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .constrainAs(symbols) {
                top.linkTo(textTop.top)
                start.linkTo(textTop.end, margin = 12.dp)
                bottom.linkTo(textTop.bottom)
            }
        ) {
            if (groupChannel.isTelegramMessage) {
                Image(
                    painter = painterResource(R.drawable.ic_vector),
                    contentDescription = "",
                    modifier = Modifier.wrapContentSize(),
                    contentScale = ContentScale.Fit,
                )
                Spacer(modifier = Modifier.padding(4.dp))
            }
            if (groupChannel.isDiscord) {
                Image(
                    painter = painterResource(R.drawable.ic_discord),
                    contentDescription = "",
                    modifier = Modifier.wrapContentSize(),
                    contentScale = ContentScale.Fit,
                )
            }
        }
        Text(
            text = groupChannel.lastMessage?.updatedAt?.toDate()?.toMessageDateFormat() ?: "",
            color = AppTheme.colors.colorTextSecondary,
            style = Typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.constrainAs(dateTime) {
                top.linkTo(textTop.top)
                bottom.linkTo(textTop.bottom)
                end.linkTo(parent.end)
            }
        )
        if (groupChannel.unreadMessageCount > 0) {
            Text(
                color = AppTheme.colors.surfaceInverse,
                text = groupChannel.unreadMessageCount.toString(),
                modifier = Modifier
                    .background(
                        color = AppTheme.colors.glow,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .wrapContentHeight()
                    .constrainAs(unreadCount) {
                        bottom.linkTo(image.bottom)
                        end.linkTo(parent.end)
                    }
                    .padding(6.dp, 2.dp, 6.dp, 2.dp),
                style = Typography.labelLarge
            )
        }
    }
}
