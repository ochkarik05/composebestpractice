package com.chisw.animation

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Indicator(
    modifier: Modifier,
    pagesCount: Int,
    state: PagerState,
) {
    Row(
        modifier,
        horizontalArrangement = Arrangement.Center,
    ) {
        repeat(pagesCount) { iteration ->
            val color by animateColorAsState(
                targetValue = if (state.currentPage == iteration) Color.DarkGray else Color.LightGray,
                label = "Pager Indicator",
            )
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(12.dp),
            )
        }
    }
}
