package com.chisw.layouts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.chisw.designsystem.component.Themed3Preview
import java.util.Random
import kotlin.math.max

@Suppress("MagicNumber")
@Composable
fun CollageLayout(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    var needToRecompose by remember {
        mutableStateOf(System.currentTimeMillis())
    }
    Layout(
        modifier = modifier.clickable {
            needToRecompose = System.currentTimeMillis()
        },
        content = content,
    ) { measurables, constraints ->
        val collageWidth = constraints.maxWidth
        var maxYOdd: Int
        var maxYEven = 0
        var maxY = 0
        val random = Random(needToRecompose)

        val placeables = measurables.mapIndexed { index, measurable ->

            val placeable = measurable.measure(Constraints(maxWidth = collageWidth / 2))

            val xPos = calculateX(index, placeable.width, collageWidth, random)
            val yPos = maxY + random.nextInt(placeable.height / 3)

            if (index % 2 == 0) {
                maxYEven = yPos + placeable.height
            } else {
                maxYOdd = yPos + placeable.height
                maxY = max(maxYOdd, maxYEven)
            }

            (xPos to yPos) to placeable
        }

        layout(collageWidth, maxY) {
            placeables.forEach { (pair, placeable) ->
                val (x, y) = pair
                placeable.placeRelative(x, y)
            }
        }
    }
}

fun calculateX(index: Int, width: Int, maxWidth: Int, random: Random): Int {
    val startPoint = (index % 2) * maxWidth / 2
    val randomPart = (maxWidth / 2 - width).takeIf { it > 0 }?.let { random.nextInt(it) } ?: 0
    return startPoint + randomPart
}

@Preview
@Composable
private fun Preview() {
    Themed3Preview {
        CollageLayout {
            Box(
                Modifier
                    .size(128.dp)
                    .background(Color.Yellow),
            )
            Box(
                Modifier
                    .size(140.dp)
                    .background(Color.Red),
            )
            Box(
                Modifier
                    .size(136.dp)
                    .background(Color.Green),
            )
            Box(
                Modifier
                    .size(144.dp)
                    .background(Color.Yellow),
            )
            Box(
                Modifier
                    .size(150.dp)
                    .background(Color.Red),
            )
            Box(
                Modifier
                    .size(130.dp)
                    .background(Color.Green),
            )
        }
    }
}
