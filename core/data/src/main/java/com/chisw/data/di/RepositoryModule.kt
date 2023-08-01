package com.chisw.data.di

import com.chisw.data.repository.ProfileRepository
import com.chisw.data.repository.ProfileRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun profile(impl: ProfileRepositoryImpl): ProfileRepository
}
