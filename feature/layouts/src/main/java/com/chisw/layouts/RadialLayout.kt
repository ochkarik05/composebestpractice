package com.chisw.layouts

import androidx.compose.animation.core.withInfiniteAnimationFrameMillis
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.chisw.designsystem.component.Themed3Preview
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

private const val TIME_DIVIDER = 2000f

@Composable
fun RadialLayout(options: List<String>, modifier: Modifier = Modifier) {
    val initialAngle by produceState(0f) {
        while (true) {
            withInfiniteAnimationFrameMillis {
                value = it / TIME_DIVIDER
            }
        }
    }

    Layout(
        modifier = modifier
            .background(Color.Yellow, CircleShape)
            .size(256.dp),
        content = {
            options.forEach { option ->
                Text(
                    text = option,
                    modifier = Modifier
                        .background(Color.Green, CircleShape)
                        .padding(4.dp),
                    color = Color.Black,
                )
            }
        },
    ) { measurables, constraints ->

        val centerX = constraints.maxWidth / 2f
        val centerY = constraints.maxHeight / 2f
        val radius = (constraints.maxHeight / 2f) // Customize the radius of the menu

        val anglePerOption = (2.0 * PI) / options.size

        layout(constraints.maxWidth, constraints.maxHeight) {
            measurables.forEachIndexed { index, measurable ->
                val angle = index * anglePerOption + initialAngle
                val x = (centerX + radius * cos(angle)).toFloat()
                val y = (centerY + radius * sin(angle)).toFloat()

                val placeable = measurable.measure(Constraints())

                placeable.placeRelative(
                    x = x.toInt() - placeable.width / 2,
                    y = y.toInt() - placeable.height / 2,
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Themed3Preview {
        RadialLayout(
            options = listOf("Uno", "Due", "Tre"),
            modifier = Modifier.padding(16.dp),
        )
    }
}
