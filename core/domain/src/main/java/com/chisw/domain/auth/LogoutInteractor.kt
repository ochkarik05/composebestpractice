package com.chisw.domain.auth

import com.chisw.auth.Auth
import javax.inject.Inject

class LogoutInteractor @Inject constructor(
    private val auth: Auth,
) {

    suspend operator fun invoke() {
        auth.logOut()
    }
}
