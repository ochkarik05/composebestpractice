package com.chisw.domain.auth

import com.chisw.auth.Auth
import me.tatarka.inject.annotations.Inject

@Inject
class LogoutInteractor constructor(
    private val auth: Auth,
) {

    suspend operator fun invoke() {
        auth.logOut()
    }
}
