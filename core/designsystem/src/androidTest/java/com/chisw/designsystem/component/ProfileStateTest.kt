package com.chisw.designsystem.component

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.chisw.data.model.Profile
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test

class ProfileStateTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun profileState_survivesConfigChanges() {
        val tester = StateRestorationTester(composeTestRule)

        tester.setContent {
            val state = rememberProfileState()
            ProfileEditor(enabled = true, profileState = state, modifier = Modifier.fillMaxWidth())
        }
        composeTestRule.onNodeWithContentDescription("Firstname").performTextInput("James")
        composeTestRule.onNodeWithContentDescription("Lastname").performTextInput("Bond")

        composeTestRule.onNodeWithText("James").assertIsDisplayed()
        composeTestRule.onNodeWithText("Bond").assertIsDisplayed()
        tester.emulateSavedInstanceStateRestore()
        composeTestRule.onNodeWithText("James").assertIsDisplayed()
        composeTestRule.onNodeWithText("Bond").assertIsDisplayed()
    }

    @Test
    fun profileProperty_reflectsFirstNameLastNameState() {
        val tester = StateRestorationTester(composeTestRule)

        tester.setContent {
            val state = rememberProfileState()
            state.firstName = "Hello"
            state.lastName = "World"
            assertThat(state.profile).isEqualTo(Profile("Hello", "World"))
        }
    }
}
