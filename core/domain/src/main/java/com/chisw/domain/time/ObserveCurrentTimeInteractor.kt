package com.chisw.domain.time

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit

private const val TIME_INTERVAL_SEC = 1L

fun interface CurrentTimeFormatter {
    fun formatTime(ms: Long): String
}

class ObserveCurrentTimeInteractor {

    class Params(
        val formatter: CurrentTimeFormatter
    )
    operator fun invoke(params: Params): Flow<String> = currentTimeFlow().map {
        params.formatter.formatTime(it)
    }

    private fun currentTimeFlow() = flow {
        while (true) {
            emit(System.currentTimeMillis())
            delay(TimeUnit.SECONDS.toMillis(TIME_INTERVAL_SEC))
        }
    }
}
