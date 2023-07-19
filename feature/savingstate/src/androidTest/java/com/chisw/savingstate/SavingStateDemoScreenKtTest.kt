package com.chisw.savingstate

import androidx.activity.ComponentActivity
import androidx.compose.material3.SnackbarDuration
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTextInput
import com.chisw.data.model.Profile
import com.chisw.designsystem.component.COUNTER_VIEW_TEST_TAG
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.properties.ReadOnlyProperty
import com.chisw.designsystem.R as designSystemR

class SavingStateDemoScreenKtTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var viewModel: SavingStateDemoViewModel
    private val eventSink: (SavingStateEvent) -> Unit = mockk(relaxed = true)
    private val onShowSnackbar: (String, String, SnackbarDuration) -> Boolean = mockk(relaxed = true)

    @Before
    fun setUp() {
        viewModel = mockk(relaxed = true)
    }

    @Test
    fun whenError_snackbarShown_and_clearErrorEvent_sent() {
        val errorText = "Error Text"

        val stateFlow = MutableStateFlow(
            SavingStateDemoViewState(
                error = IllegalArgumentException(errorText),
                eventSink = eventSink,
            ),
        )
        every { viewModel.state } returns stateFlow
        every { onShowSnackbar.invoke(errorText, "OK", SnackbarDuration.Indefinite) } returns true

        composeTestRule.setContent {
            SavingStateDemoScreen(onShowSnackBar = onShowSnackbar, viewModel = viewModel)
        }

        verify { onShowSnackbar.invoke(errorText, "OK", SnackbarDuration.Indefinite) }
        verify { eventSink.invoke(SavingStateEvent.ClearErrors) }
    }

    @Test
    fun onLogoutClick_snackbarShown_and_LogoutEvent_sent() {
        val stateFlow = MutableStateFlow(
            SavingStateDemoViewState(
                eventSink = eventSink,
            ),
        )
        every { viewModel.state } returns stateFlow
        every { onShowSnackbar.invoke(any(), any(), any()) } returns true

        composeTestRule.setContent {
            SavingStateDemoScreen(onShowSnackBar = onShowSnackbar, viewModel = viewModel)
        }

        val confirmLogout by composeTestRule.stringResource(R.string.confirm_logout)

        composeTestRule.onNodeWithTag(SAVING_STATE_DEMO_SCREEN_TAG).performScrollToNode(hasText("Logout"))
        composeTestRule.onNodeWithText("Logout").performClick()

        verify { onShowSnackbar.invoke(confirmLogout, "OK", SnackbarDuration.Short) }
        verify { eventSink.invoke(SavingStateEvent.Logout) }
    }

    @Test
    fun onIncrementClick_IncrementEventSent() {
        val stateFlow = MutableStateFlow(
            SavingStateDemoViewState(
                eventSink = eventSink,
            ),
        )
        every { viewModel.state } returns stateFlow

        composeTestRule.setContent {
            SavingStateDemoScreen(onShowSnackBar = onShowSnackbar, viewModel = viewModel)
        }

        composeTestRule.onNode(
            hasText("+") and hasParent(
                hasTestTag(COUNTER_VIEW_TEST_TAG),
            ),
        ).performClick()

        verify { eventSink.invoke(SavingStateEvent.Increment) }
    }

    @Test
    fun onDecrementClick_IncrementEventSent() {
        val stateFlow = MutableStateFlow(
            SavingStateDemoViewState(
                eventSink = eventSink,
            ),
        )
        every { viewModel.state } returns stateFlow

        composeTestRule.setContent {
            SavingStateDemoScreen(onShowSnackBar = onShowSnackbar, viewModel = viewModel)
        }

        composeTestRule.onNode(
            hasText("+") and hasParent(
                hasTestTag(COUNTER_VIEW_TEST_TAG),
            ),
        ).performClick()

        verify { eventSink.invoke(SavingStateEvent.Increment) }
    }

    @Test
    fun currentStep_IsDisplayed() {
        val count = 100
        val stateFlow = MutableStateFlow(
            SavingStateDemoViewState(
                currentStep = count,
                eventSink = eventSink,
            ),
        )
        every { viewModel.state } returns stateFlow

        composeTestRule.setContent {
            SavingStateDemoScreen(onShowSnackBar = onShowSnackbar, viewModel = viewModel)
        }

        composeTestRule.onNodeWithText(count.toString()).assertIsDisplayed()
    }

    @Test
    fun firstNameLastName_IsDisplayed() {
        val firstName = "James"
        val lastName = "Bond"
        val stateFlow = MutableStateFlow(
            SavingStateDemoViewState(
                profile = Profile(firstName, lastName),
                eventSink = eventSink,
            ),
        )
        every { viewModel.state } returns stateFlow

        composeTestRule.setContent {
            SavingStateDemoScreen(onShowSnackBar = onShowSnackbar, viewModel = viewModel)
        }

        composeTestRule.onNodeWithTag(SAVING_STATE_DEMO_SCREEN_TAG).performScrollToNode(hasText("Logout"))
        composeTestRule.onNodeWithText(firstName).assertIsDisplayed()
        composeTestRule.onNodeWithText(lastName).assertIsDisplayed()
    }

    @Test
    fun onSaveClick_SaveProfileEvent_isSent() {
        val stateFlow = MutableStateFlow(
            SavingStateDemoViewState(
                eventSink = eventSink,
            ),
        )
        every { viewModel.state } returns stateFlow

        composeTestRule.setContent {
            SavingStateDemoScreen(onShowSnackBar = onShowSnackbar, viewModel = viewModel)
        }
        val lastname by composeTestRule.stringResource(designSystemR.string.lastname)
        val save by composeTestRule.stringResource(designSystemR.string.save)

        composeTestRule.onNodeWithTag(SAVING_STATE_DEMO_SCREEN_TAG).performScrollToNode(hasText("Logout"))
        composeTestRule.onNodeWithContentDescription(lastname).performTextInput("x")
        composeTestRule.onNodeWithText(save).performClick()
        verify { eventSink.invoke(any()) }
    }
}

private fun AndroidComposeTestRule<*, *>.stringResource(resId: Int) = ReadOnlyProperty<Any?, String> { _, _ ->
    activity.getString(resId)
}
