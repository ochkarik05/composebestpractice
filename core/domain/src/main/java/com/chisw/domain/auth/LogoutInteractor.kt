package com.chisw.domain.auth

import com.chisw.auth.Auth
import com.chisw.auth.AuthImpl

class LogoutInteractor(
    private val auth: Auth,
) {

    constructor() : this(AuthImpl)

    suspend operator fun invoke() {
        auth.logOut()
    }
}
