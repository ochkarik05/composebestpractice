package com.chisw.common.di

import com.chisw.common.AppLogger
import com.chisw.common.Logger
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface LoggerModule {
    @Binds
    fun logger(impl: AppLogger): Logger
}
