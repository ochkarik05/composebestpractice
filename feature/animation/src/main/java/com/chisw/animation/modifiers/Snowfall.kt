package com.chisw.animation.modifiers

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import com.chisw.animation.R
import kotlinx.coroutines.isActive
import java.util.concurrent.ThreadLocalRandom
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.time.Duration.Companion.nanoseconds

enum class SnowFallType {
    FLYING_CIRCLES,
    SNOWFLAKES,
}

fun Modifier.snowfall(type: SnowFallType = SnowFallType.SNOWFLAKES) = composed {
    val flakes = when (type) {
        SnowFallType.FLYING_CIRCLES -> emptyList()
        SnowFallType.SNOWFLAKES -> listOf(
            painterResource(id = R.drawable.flake_1),
            painterResource(id = R.drawable.flake_2),
            painterResource(id = R.drawable.flake_3),
            painterResource(id = R.drawable.flake_4),
            painterResource(id = R.drawable.flake_1),
            painterResource(id = R.drawable.flake_2),
            painterResource(id = R.drawable.flake_3),
            painterResource(id = R.drawable.flake_4),
        )
    }

    var snowflakesState by remember {
        mutableStateOf(
            SnowflakesState(
                -1,
                IntSize(0, 0),
                if (flakes.isEmpty()) {
                    null
                } else {
                    { flakes }
                },
            ),
        )
    }

    LaunchedEffect(Unit) {
        while (isActive) {
            withFrameNanos { newTick ->
                val elapsedMillis =
                    (newTick - snowflakesState.tickNanos).nanoseconds.inWholeMilliseconds
                val wasFirstRun = snowflakesState.tickNanos < 0
                snowflakesState.tickNanos = newTick

                if (wasFirstRun) return@withFrameNanos
                for (snowflake in snowflakesState.snowflakes) {
                    snowflake.update(elapsedMillis)
                }
            }
        }
    }

    onSizeChanged { newSize -> snowflakesState = snowflakesState.resize(newSize) }
        .clipToBounds()
        .drawWithContent {
            drawContent()
            snowflakesState.draw(this)
        }
}

private fun ClosedRange<Float>.random() =
    ThreadLocalRandom.current().nextFloat() * (endInclusive - start) + start

private fun Float.random() =
    ThreadLocalRandom.current().nextFloat() * this

private fun Int.random() =
    ThreadLocalRandom.current().nextInt(this)

private fun IntSize.randomPosition() =
    Offset(width.random().toFloat(), height.random().toFloat())

private const val snowflakeDensity = 0.05
private val incrementRange = 0.2f..0.8f
private val sizeRange = 5.0f..12.0f
private const val angleSeed = 100.0f
private val angleSeedRange = -angleSeed..angleSeed
private const val angleRange = 0.1f
private const val angleDivisor = 10000.0f

internal data class SnowflakesState(
    var tickNanos: Long,
    val snowflakes: List<Snowflake>,
    val flakes: (() -> List<Painter>)?,
) {

    constructor(
        tick: Long,
        canvasSize: IntSize,
        flakes: (() -> List<Painter>)? = null,
    ) : this(tick, createSnowFlakes(flakes, canvasSize), flakes)

    fun draw(contentDrawScope: ContentDrawScope) {
        snowflakes.forEach {
            it.draw(contentDrawScope)
        }
    }

    fun resize(newSize: IntSize) = copy(snowflakes = createSnowFlakes(flakes, newSize))

    companion object {

        fun createSnowFlakes(
            flakesProvider: (() -> List<Painter>)?,
            canvasSize: IntSize,
        ): List<Snowflake> {
            return if (flakesProvider == null) {
                createRoundSnowflakes(canvasSize)
            } else {
                createNaturalSnowflakes(canvasSize, flakesProvider)
            }
        }

        private fun createNaturalSnowflakes(
            canvasSize: IntSize,
            painterProvider: () -> List<Painter>,
        ): List<NaturalSnowflake> {
            if (canvasSize.height == 0 || canvasSize.width == 0) {
                return emptyList()
            }

            val painters = painterProvider.invoke()

            val snowflakesCount = (3..painters.size).random()

            return List(snowflakesCount) {
                NaturalSnowflake(
                    incrementFactor = incrementRange.random(),
                    flakeSize = sizeRange.random(),
                    canvasSize = canvasSize,
                    maxAlpha = (0.1f..0.7f).random(),
                    painter = painters[it],
                    position = canvasSize.randomPosition(),
                )
            }
        }

        private fun createRoundSnowflakes(canvasSize: IntSize): List<RoundSnowflake> {
            val canvasArea = canvasSize.width * canvasSize.height
            val normalizedDensity = snowflakeDensity.coerceIn(0.0..1.0) / 1000.0
            val snowflakesCount = (canvasArea * normalizedDensity).roundToInt()

            return List(snowflakesCount) {
                RoundSnowflake(
                    incrementFactor = incrementRange.random(),
                    size = sizeRange.random(),
                    canvasSize = canvasSize,
                    position = canvasSize.randomPosition(),
                    angle = angleSeed.random() / angleSeed * angleRange + (PI / 2.0) - (angleRange / 2.0),
                )
            }
        }
    }
}

private val snowflakePaint = Paint().apply {
    isAntiAlias = true
    color = Color.White
    style = PaintingStyle.Fill
    alpha = 0.2f
}

interface Snowflake {
    fun update(elapsedMillis: Long)
    fun draw(contentDrawScope: ContentDrawScope)
}

internal class NaturalSnowflake(
    private val incrementFactor: Float,
    flakeSize: Float,
    position: Offset,
    private val canvasSize: IntSize,
    private val maxAlpha: Float,
    private val painter: Painter,
) : Snowflake {
    init {
        require(maxAlpha >= 0)
        require(maxAlpha <= 1)
    }

    private val flakeSizeFactor = 8

    private val flakeSizeActual = flakeSize * flakeSizeFactor
    private var position by mutableStateOf(
        position,
    )
    private var alpha by mutableStateOf(0.001f)
    private var increasing by mutableStateOf(true)

    private val baseFrameDurationMillis = 16

    override fun update(elapsedMillis: Long) {
        val increment =
            incrementFactor * (elapsedMillis / baseFrameDurationMillis) / 100f
        alpha = if (increasing) {
            alpha + increment
        } else {
            alpha - increment
        }.coerceIn(0f, maxAlpha)

        if (alpha == maxAlpha) {
            increasing = false
        }

        if (alpha == 0f) {
            increasing = true
            alpha = 0.001f
            position = canvasSize.randomPosition()
        }
    }

    override fun draw(contentDrawScope: ContentDrawScope) {
        with(contentDrawScope) {
            translate(
                position.x,
                position.y,
            ) {
                with(painter) {
                    draw(
                        Size(flakeSizeActual, flakeSizeActual),
                        alpha,
                        ColorFilter.tint(Color.White),
                    )
                }
            }
        }
    }
}

internal class RoundSnowflake(
    private val incrementFactor: Float,
    private val size: Float,
    private val canvasSize: IntSize,
    position: Offset,
    angle: Double,
) : Snowflake {

    private val baseFrameDurationMillis = 16
    private val baseSpeedPxAt60Fps = 5
    private var position by mutableStateOf(position)
    private var angle by mutableStateOf(angle)

    override fun update(elapsedMillis: Long) {
        val increment =
            incrementFactor * (elapsedMillis / baseFrameDurationMillis) * baseSpeedPxAt60Fps
        val xDelta = (increment * cos(angle)).toFloat()
        val yDelta = (increment * sin(angle)).toFloat()
        position = Offset(position.x + xDelta, position.y + yDelta)
        angle += angleSeedRange.random() / angleDivisor

        if (position.y > canvasSize.height + size) {
            position = Offset(canvasSize.width.random().toFloat(), -size)
        }
    }

    override fun draw(contentDrawScope: ContentDrawScope) {
        contentDrawScope.drawContext.canvas.drawCircle(position, size, snowflakePaint)
    }
}
