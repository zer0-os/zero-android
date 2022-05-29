package com.zero.android.feature.network.drawer.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.zero.android.ui.theme.ZeroExtendedTheme

@Composable
fun DrawerItem(
    item: NetworkWorld,
    onItemClick: (NetworkWorld) -> Unit
) {
    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .clickable { onItemClick.invoke(item) }) {
        val (image, textTop, textBottom, textEnd) = createRefs()

        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        )
        Image(
            //painter = painterResource(),
            contentDescription = item.title,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(textTop.start)
                }
                .size(42.dp)
                .clip(CircleShape)
        )
        Text(
            text = item.title,
            modifier = Modifier.constrainAs(textTop) {
                top.linkTo(parent.top)
                bottom.linkTo(textBottom.top)
                start.linkTo(image.end)
                end.linkTo(textEnd.start)
            },
            color = ZeroExtendedTheme.colors.colorTextPrimary,
            fontSize = 16.sp
        )
        Text(
            text = item.domain,
            modifier = Modifier.constrainAs(textBottom) {
                top.linkTo(textTop.bottom)
                bottom.linkTo(parent.bottom)
                start.linkTo(textTop.start)
                end.linkTo(textTop.end)
            },
            color = ZeroExtendedTheme.colors.colorTextSecondary
        )
        Text(
            text = item.unreadCount.toString(),
            modifier = Modifier
                .constrainAs(textEnd) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(textTop.end)
                    end.linkTo(parent.end)
                }
                .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(30.dp))
                .padding(horizontal = 6.dp, vertical = 2.dp),
            color = ZeroExtendedTheme.colors.colorTextSecondary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = false)
@Composable
fun DrawerItemPreview() {

}
