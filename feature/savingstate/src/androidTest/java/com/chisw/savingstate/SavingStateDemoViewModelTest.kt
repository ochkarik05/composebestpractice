package com.chisw.savingstate

import androidx.lifecycle.SavedStateHandle
import com.chisw.common.Logger
import com.chisw.data.model.Profile
import com.chisw.domain.auth.LogoutInteractor
import com.chisw.domain.profile.ObserveProfileInteractor
import com.chisw.domain.profile.SaveProfileInteractor
import com.chisw.domain.time.ObserveCurrentTimeInteractor
import com.google.common.truth.Truth.assertThat
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class SavingStateDemoViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    val dispatcher = StandardTestDispatcher()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = CoroutinesTestRule(dispatcher)

    private val observeCurrentTimeInteractor = mockk<ObserveCurrentTimeInteractor>()
    private val logoutInteractor = mockk<LogoutInteractor>(relaxed = true)
    private val saveProfileInteractor = mockk<SaveProfileInteractor>(relaxed = true)
    private val observeProfileInteractor = mockk<ObserveProfileInteractor>(relaxed = true)
    private val logger = mockk<Logger>(relaxed = true)

    private lateinit var sut: SavingStateDemoViewModel
    private lateinit var savedStateHandle: SavedStateHandle

    @Before
    fun setUp() {
        savedStateHandle = SavedStateHandle()
        sut = SavingStateDemoViewModel(
            savedStateHandle,
            observeCurrentTimeInteractor,
            logoutInteractor,
            saveProfileInteractor,
            observeProfileInteractor,
            logger,
            mockk(),
            mockk(),
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun whenCurrentStepIsIncremented_itIsDeliveredToViewState() = runTest(dispatcher) {
        every { observeCurrentTimeInteractor.invoke(any()) } returns flowOf("21:00")
        every { observeProfileInteractor.invoke() } returns flowOf(Profile())
        val job = launch {
            sut.state.take(2).collect()
        }
        sut.state.value.eventSink(SavingStateEvent.Increment)
        advanceUntilIdle()
        job.cancelAndJoin()
        assertThat(sut.state.value.currentStep).isEqualTo(1)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun whenCurrentStepIsDecremented_itIsDeliveredToViewState() = runTest(dispatcher) {
        every { observeCurrentTimeInteractor.invoke(any()) } returns flowOf("21:00")
        every { observeProfileInteractor.invoke() } returns flowOf(Profile())
        val job = launch {
            sut.state.take(2).collect()
        }
        sut.state.value.eventSink(SavingStateEvent.Decrement)
        advanceUntilIdle()
        job.cancelAndJoin()
        assertThat(sut.state.value.currentStep).isEqualTo(-1)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun test_eventSink_with_Logout_event() = runTest {
        every { observeCurrentTimeInteractor.invoke(any()) } returns flowOf("")
        sut.state.value.eventSink(SavingStateEvent.Logout)
        advanceUntilIdle()
        coVerify { logoutInteractor.invoke() }
    }
}

@ExperimentalCoroutinesApi
class CoroutinesTestRule(
    private val testDispatcher: TestDispatcher = StandardTestDispatcher(),
) : TestWatcher() {

    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}
