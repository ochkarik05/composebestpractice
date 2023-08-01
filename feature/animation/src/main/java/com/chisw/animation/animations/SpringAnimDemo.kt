package com.chisw.animation.animations

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.chisw.animation.R
import com.chisw.designsystem.component.ChiButton
import com.chisw.designsystem.component.Themed3Preview

private enum class BallPosition {
    START,
    END,
}

@Composable
fun SpringAnimDemo(modifier: Modifier = Modifier) {
    var ballPosition by remember {
        mutableStateOf(BallPosition.START)
    }

    val onClick = remember {
        {
            ballPosition = when (ballPosition) {
                BallPosition.START -> BallPosition.END
                BallPosition.END -> BallPosition.START
            }
        }
    }

    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(stringResource(R.string.spring_demo_title))
        SpringDefault(ballPosition)
        SpringDumpingHiStiffnessMedium(ballPosition)
        SpringDampingHiStiffnessVeryLow(ballPosition)
        Keyframes(ballPosition)
        StartAnimationButton(ballPosition, modifier = Modifier.align(CenterHorizontally), onClick = onClick)
    }
}

@Composable
private fun Keyframes(ballPosition: BallPosition) {
    Text(stringResource(R.string.keyframe_animation))
    @Suppress("MagicNumber")
    BouncingBallDemo(
        ballPosition = ballPosition,
        animationSpec =

        when (ballPosition) {
            BallPosition.START -> keyframes {
                durationMillis = 800
                IntOffset(500, 0) at 400 with LinearOutSlowInEasing
            }

            BallPosition.END -> keyframes {
                durationMillis = 800
                IntOffset(0, 0) at 0
                IntOffset(100, 0) at 400 with LinearOutSlowInEasing
            }
        },
    )
}

@Composable
private fun SpringDampingHiStiffnessVeryLow(ballPosition: BallPosition) {
    Text(stringResource(R.string.spring_damping_hi_stiffness_very_low))
    BouncingBallDemo(
        ballPosition = ballPosition,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessVeryLow,
        ),
    )
}

@Composable
private fun SpringDumpingHiStiffnessMedium(ballPosition: BallPosition) {
    Text(stringResource(R.string.spring_damping_hi_stiffness_medium))
    BouncingBallDemo(
        ballPosition = ballPosition,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
        ),
    )
}

@Composable
private fun SpringDefault(ballPosition: BallPosition) {
    Text(stringResource(R.string.sping_default))
    BouncingBallDemo(ballPosition)
}

@Composable
private fun BouncingBallDemo(
    ballPosition: BallPosition,
    modifier: Modifier = Modifier,
    animationSpec: AnimationSpec<IntOffset> = spring(visibilityThreshold = IntOffset.VisibilityThreshold),
) {
    var width by remember {
        mutableStateOf(0)
    }
    val ballSize = 16.dp
    val ballSizePx = with(LocalDensity.current) {
        ballSize.toPx()
    }

    val offset by animateIntOffsetAsState(
        targetValue = when (ballPosition) {
            BallPosition.START -> IntOffset(0, 0)
            BallPosition.END -> IntOffset(width - ballSizePx.toInt(), 0)
        },
        animationSpec = animationSpec,
        label = "Ball Position",
    )

    Box(
        modifier
            .fillMaxWidth()
            .onSizeChanged {
                width = it.width
            },
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(16.dp)
                .background(Color.LightGray, RoundedCornerShape(8.dp)),
        )
        Box(
            Modifier
                .size(ballSize)
                .height(16.dp)
                .offset { offset }
                .background(Color.Red, CircleShape),
        )
    }
}

@Composable
@OptIn(ExperimentalAnimationApi::class)
private fun StartAnimationButton(
    ballPosition: BallPosition,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    ChiButton(onClick = onClick, modifier = modifier) {
        AnimatedContent(targetState = ballPosition, label = "Animate Button") {
            val stringResource = stringResource(R.string.press_to_animate)
            val text = when (ballPosition) {
                BallPosition.START -> {
                    "$stringResource ->"
                }

                BallPosition.END -> "<- $stringResource"
            }
            Text(
                text,
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Themed3Preview {
        SpringAnimDemo()
    }
}
