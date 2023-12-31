package com.chisw.auth

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import me.tatarka.inject.annotations.Inject

@Inject
class AuthImpl : Auth {

    private val state = MutableStateFlow(false)

    override fun isLoggedIn(): StateFlow<Boolean> = state.asStateFlow()

    override suspend fun login() {
        state.value = true
    }

    override suspend fun logOut() {
        state.value = false
    }
}
