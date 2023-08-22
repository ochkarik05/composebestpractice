package com.chisw.common.di

import com.chisw.common.AppLogger
import com.chisw.common.Logger
import me.tatarka.inject.annotations.Provides

interface LoggerComponent {

    val logger: Logger
    val appLogger: AppLogger

    val AppLogger.bind: Logger
        @Singleton
        @Provides
        get() = this
}
