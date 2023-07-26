package com.chisw.auth

import kotlinx.coroutines.flow.StateFlow

interface Auth {

    fun isLoggedIn(): StateFlow<Boolean>
    suspend fun login()
    suspend fun logOut()
}
