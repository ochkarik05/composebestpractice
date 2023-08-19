package com.chisw.animation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chisw.designsystem.component.ChiHeader
import com.chisw.designsystem.component.Themed3Preview

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnimationDemoScreen() {
    val pages: List<AnimPages> = pages()
    val state: PagerState = rememberPagerState { pages.size }
    Column(
        modifier = Modifier.padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        ChiHeader(text = stringResource(id = R.string.animation_demo_screen))
        AnimPager(
            state = state,
            pages = pages,
            modifier = Modifier.weight(1f),
        )
        Indicator(
            modifier = Modifier
                .height(32.dp)
                .align(Alignment.CenterHorizontally),
            pagesCount = pages.size,
            state = state,
        )
    }
}

@Preview
@Composable
private fun Preview() {
    Themed3Preview {
        AnimationDemoScreen()
    }
}
