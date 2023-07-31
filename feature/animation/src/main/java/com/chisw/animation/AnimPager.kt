package com.chisw.animation

import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import android.os.Build
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

private const val BW_SHADER = """
uniform shader composable;
uniform float2 size;
half4 main(float2 fragCoord)  {
    half4 color = composable.eval(fragCoord);
    color.rgb = half3(dot(color.rgb, half3(0.2126, 0.7152, 0.0722)));
    return color;
}
"""

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun AnimPager(
    modifier: Modifier = Modifier,
    pages: List<AnimPages> = pages(),
    state: PagerState = rememberPagerState(),
    pageContent: @Composable (AnimPages) -> Unit = {
        AnimPage(animPage = it)
    },
) {
    val isScrolling by remember(state.currentPageOffsetFraction) {
        derivedStateOf {
            state.currentPageOffsetFraction != 0.0f
        }
    }
    val pageSize by animateFloatAsState(targetValue = if (isScrolling) 0.95f else 1f, label = "")

    val shader = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        RuntimeShader(BW_SHADER)
    } else {
        null
    }

    HorizontalPager(
        state = state,
        modifier = modifier,
        pageCount = pages.size,
    ) {
        Card(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
                .then(
                    if (shader != null && isScrolling) {
                        Modifier.graphicsLayer {
                            clip = true
                            scaleX = pageSize
                            scaleY = pageSize
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                renderEffect = RenderEffect
                                    .createRuntimeShaderEffect(shader, "composable")
                                    .asComposeRenderEffect()
                            }
                        }
                    } else {
                        Modifier
                    },
                ),
        ) {
            pageContent(pages[it])
        }
    }
}

@Composable
fun pages(): List<AnimPages> = remember {
    listOf(
        AnimPages.SPRING,
        AnimPages.VECTOR,
        AnimPages.SNOWFALL,
    )
}
