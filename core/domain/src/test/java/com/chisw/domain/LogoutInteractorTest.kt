package com.chisw.domain

import com.chisw.auth.Auth
import com.chisw.domain.auth.LogoutInteractor
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

class LogoutInteractorTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `interactor calls auth logout method`() = runTest {
        // Arrange
        val auth: Auth = mockk(relaxed = true)
        val sut = LogoutInteractor(auth)
        // Act
        sut()
        // Assert
        coVerify { auth.logOut() }
    }
}
