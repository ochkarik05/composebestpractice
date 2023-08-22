package com.chisw.data.di

import com.chisw.data.repository.ProfileRepository
import com.chisw.data.repository.ProfileRepositoryImpl
import me.tatarka.inject.annotations.Provides

interface RepositoryComponent {
    val ProfileRepositoryImpl.bind: ProfileRepository
        @Provides get() = this
}
