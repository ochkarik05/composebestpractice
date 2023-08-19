package com.chisw.domain.di

import com.chisw.domain.utils.AppCoroutineDispatchers
import me.tatarka.inject.annotations.Provides

interface DomainComponent {
    @Provides
    fun coroutineDispatchers() = AppCoroutineDispatchers()
}

// fun createAuthComponent(): AuthComponent {
//    return AuthComponent::class.create()
// }
//
// fun createRepositoryComponent(): RepositoryComponent {
//    return RepositoryComponent::class.create()
// }
//
// fun createLoggerComponent(): LoggerComponent {
//    return LoggerComponent::class.create()
// }
