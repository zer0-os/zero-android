package com.zero.android.feature.channels.ui.channels

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.zero.android.common.R
import com.zero.android.common.extensions.toDate
import com.zero.android.common.extensions.toMessageDateFormat
import com.zero.android.models.Channel
import com.zero.android.models.DirectChannel
import com.zero.android.models.GroupChannel
import com.zero.android.models.enums.MessageStatus
import com.zero.android.models.getTitle
import com.zero.android.ui.components.LargeCircularImage
import com.zero.android.ui.theme.AppTheme
import com.zero.android.ui.theme.Typography

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ChannelPager(
    pagerState: PagerState,
    groupChannelUiState: GroupChannelUiState,
    onClick: (Channel) -> Unit
) {
    val categories = (groupChannelUiState.categoriesUiState as ChannelCategoriesUiState.Success).categories
    HorizontalPager(
        state = pagerState,
        count = categories.size
    ) { index ->
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn {
                groupChannelUiState.getChannels(categories[index].name).let {
                    items(it) { channel ->
                        ChannelsItemsList(channel = channel, onClick = onClick)
                    }
                }
            }
        }
    }
}

@Composable
fun ChannelsItemsList(
    loggedInUserId: String? = null,
    channel: Channel,
    onClick: (Channel) -> Unit
) {
    val isDirectChannel = channel is DirectChannel

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(12.dp)
            .clickable { onClick(channel) }
    ) {
        val (image, textTop, textBottom, dateTime, unreadCount) = createRefs()

        LargeCircularImage(
            modifier = Modifier
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(textTop.start)
                },
            placeHolder = R.drawable.ic_circular_image_placeholder,
            imageUrl = channel.coverUrl,
            contentDescription = channel.id
        )
        Row(
            modifier = Modifier.constrainAs(textTop) {
                top.linkTo(image.top)
                bottom.linkTo(textBottom.top)
                start.linkTo(image.end, margin = 12.dp)
                end.linkTo(dateTime.start, margin = 12.dp)
                width = Dimension.fillToConstraints
            }
        ) {
            Text(
                text = channel.getTitle(loggedInUserId),
                color = AppTheme.colors.colorTextPrimary,
                style = Typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(CenterVertically),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            if (!isDirectChannel) {
                if ((channel as GroupChannel).hasTelegramChannel) {
                    Spacer(modifier = Modifier.padding(4.dp))
                    Image(
                        painter = painterResource(R.drawable.ic_vector),
                        contentDescription = "",
                        modifier = Modifier
                            .wrapContentSize()
                            .align(CenterVertically),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                }
                if ((channel as GroupChannel).hasDiscordChannel) {
                    Image(
                        painter = painterResource(R.drawable.ic_discord),
                        contentDescription = "",
                        modifier = Modifier
                            .wrapContentSize()
                            .align(CenterVertically),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                }
            }
        }
        Text(
            text = channel.lastMessage?.message ?: "",
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
        Text(
            text = channel.lastMessage?.createdAt?.toDate()?.toMessageDateFormat() ?: "",
            color = AppTheme.colors.colorTextSecondary,
            style = Typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.constrainAs(dateTime) {
                top.linkTo(textTop.top)
                bottom.linkTo(textTop.bottom)
                end.linkTo(parent.end)
            }
        )
        if (channel is DirectChannel) {
            if (channel.lastMessage?.author?.id?.equals(loggedInUserId, true) == true) {
                Icon(
                    painter = if (channel.lastMessage?.status == MessageStatus.PENDING) {
                        painterResource(R.drawable.ic_check)
                    } else {
                        painterResource(R.drawable.ic_double_check)
                    },
                    contentDescription = "cd_message_status",
                    modifier = Modifier
                        .width(18.dp)
                        .constrainAs(unreadCount) {
                            bottom.linkTo(image.bottom)
                            end.linkTo(parent.end)
                        }
                )
            } else {
                if (channel.unreadMessageCount > 0) {
                    UnReadCountText(
                        modifier = Modifier
                            .constrainAs(unreadCount) {
                                bottom.linkTo(image.bottom)
                                end.linkTo(parent.end)
                            },
                        text = channel.unreadMessageCount.toString()
                    )
                }
            }
        } else {
            if (channel.unreadMessageCount > 0) {
                UnReadCountText(
                    modifier = Modifier
                        .constrainAs(unreadCount) {
                            bottom.linkTo(image.bottom)
                            end.linkTo(parent.end)
                        },
                    text = channel.unreadMessageCount.toString()
                )
            }
        }
    }
}

@Composable
fun UnReadCountText(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        color = AppTheme.colors.surfaceInverse,
        text = text,
        modifier = modifier
            .background(
                color = AppTheme.colors.glow,
                shape = RoundedCornerShape(24.dp)
            )
            .wrapContentHeight()
            .padding(6.dp, 2.dp, 6.dp, 2.dp),
        style = Typography.labelLarge
    )
}
