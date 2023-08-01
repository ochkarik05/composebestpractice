package com.chisw.di

import com.chisw.auth.Auth
import com.chisw.auth.AuthImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AuthModule {
    @Binds
    fun auth(impl: AuthImpl): Auth
}
