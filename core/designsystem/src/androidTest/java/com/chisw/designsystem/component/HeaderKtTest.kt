package com.chisw.designsystem.component

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class HeaderKtTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun theGivenTest_isShown() {
        val text = "Header"
        composeTestRule.setContent {
            ChiHeader(text = text)
        }
        composeTestRule.onNodeWithText(text).assertIsDisplayed()
    }
}
