package com.chisw.di

import com.chisw.auth.Auth
import com.chisw.auth.AuthImpl
import com.chisw.common.di.Singleton
import me.tatarka.inject.annotations.Provides

interface AuthComponent {
    val auth: Auth

    val AuthImpl.binds: Auth
        @Singleton @Provides
        get() = this
}
