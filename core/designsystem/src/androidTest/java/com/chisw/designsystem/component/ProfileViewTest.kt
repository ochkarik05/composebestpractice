package com.chisw.designsystem.component

import androidx.activity.ComponentActivity
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.chisw.data.model.Profile
import com.chisw.designsystem.R
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import kotlin.properties.ReadOnlyProperty

class ProfileViewTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val initialProfile = Profile("James", "Bond")

    private val saveButton by composeTestRule.stringResource(R.string.save)
    private val logoutButton by composeTestRule.stringResource(R.string.logout)
    private val firstname by composeTestRule.stringResource(R.string.firstname)
    private val lastname by composeTestRule.stringResource(R.string.lastname)

    @Test
    fun profileView_hasFirstNameVisible() {
        composeTestRule.setContent {
            ProfileView(
                initialProfile = initialProfile,
                isLoading = false,
                onUpdate = {},
                onLogout = {},
            )
        }

        composeTestRule.onNodeWithText("James").assertIsDisplayed()
    }

    @Test
    fun profileView_hasLastNameVisible() {
        composeTestRule.setContent {
            ProfileView(
                initialProfile = initialProfile,
                isLoading = false,
                onUpdate = {},
                onLogout = {},
            )
        }

        composeTestRule.onNodeWithText("Bond").assertIsDisplayed()
    }

    @Test
    fun clickOnSave_callsOnUpdateWithCorrectProfile() {
        val onUpdate: (Profile) -> Unit = mockk(relaxed = true)
        composeTestRule.setContent {
            ProfileView(
                initialProfile = Profile("", ""),
                isLoading = false,
                onUpdate = onUpdate,
                onLogout = {},
            )
        }
        val name = "Taras"
        val surname = "Shevchenko"
        composeTestRule.onNodeWithContentDescription(firstname).performTextInput(name)
        composeTestRule.onNodeWithContentDescription(lastname).performTextInput(surname)
        composeTestRule.onNodeWithText(saveButton).performClick()
        verify { onUpdate.invoke(Profile(firstName = name, lastName = surname)) }
    }

    @Test
    fun clickOnLogout_callsOnLogout() {
        val onLogout: () -> Unit = mockk(relaxed = true)
        composeTestRule.setContent {
            ProfileView(
                initialProfile = initialProfile,
                isLoading = false,
                onUpdate = {},
                onLogout = onLogout,
            )
        }
        composeTestRule.onNodeWithText(logoutButton).performClick()
        verify { onLogout() }
    }

    @Test
    fun saveButton_isDisabled_initially() {
        val onUpdate: (Profile) -> Unit = mockk(relaxed = true)
        composeTestRule.setContent {
            ProfileView(
                initialProfile = initialProfile,
                isLoading = false,
                onUpdate = onUpdate,
                onLogout = {},
            )
        }
        composeTestRule.onNodeWithText(saveButton).assertIsNotEnabled()
    }

    @Test
    fun saveButton_isEnabled_afterFirstnameChanged() {
        val onUpdate: (Profile) -> Unit = mockk(relaxed = true)
        composeTestRule.setContent {
            ProfileView(
                initialProfile = initialProfile,
                isLoading = false,
                onUpdate = onUpdate,
                onLogout = {},
            )
        }
        composeTestRule.onNodeWithContentDescription(firstname).performTextInput("x")
        composeTestRule.onNodeWithText(saveButton).assertIsEnabled()
    }

    @Test
    fun saveButton_isEnabled_afterLastnameChanged() {
        val onUpdate: (Profile) -> Unit = mockk(relaxed = true)
        composeTestRule.setContent {
            ProfileView(
                initialProfile = initialProfile,
                isLoading = false,
                onUpdate = onUpdate,
                onLogout = {},
            )
        }
        composeTestRule.onNodeWithContentDescription(lastname).performTextInput("x")
        composeTestRule.onNodeWithText(saveButton).assertIsEnabled()
    }

    @Test
    fun whenIsLoading_elementsAreDisabled() {
        val loading = mutableStateOf(false)
        composeTestRule.setContent {
            ProfileView(
                initialProfile = initialProfile,
                isLoading = loading.value,
                onUpdate = {},
                onLogout = {},
            )
        }

        composeTestRule.onNodeWithContentDescription(lastname).performTextInput("x")
        loading.value = true
        composeTestRule.onNodeWithText(saveButton).assertIsNotEnabled()
        composeTestRule.onNodeWithText(logoutButton).assertIsNotEnabled()
        composeTestRule.onNodeWithContentDescription(firstname).assertIsNotEnabled()
        composeTestRule.onNodeWithContentDescription(lastname).assertIsNotEnabled()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun whenIsLoading_alphaBeginAnimating() {
        val loading = mutableStateOf(false)
        composeTestRule.setContent {
            ProfileView(
                initialProfile = initialProfile,
                isLoading = loading.value,
                onUpdate = {},
                onLogout = {},
            )
        }
        composeTestRule.onNodeWithAlpha(1f).assertExists()
        loading.value = true

        composeTestRule.waitUntilAtLeastOneExists(
            SemanticsMatcher.expectValue(AlphaKey, ALPHA_MAX),
        )

        composeTestRule.onNodeWithAlpha(ALPHA_MAX).assertExists()
    }
}

private fun AndroidComposeTestRule<*, *>.stringResource(resId: Int) = ReadOnlyProperty<Any?, String> { _, _ ->
    activity.getString(resId)
}

private fun ComposeTestRule.onNodeWithAlpha(alpha: Float) = onNode(
    SemanticsMatcher.expectValue(AlphaKey, alpha),
)
