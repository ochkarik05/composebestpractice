package com.chisw.designsystem.component

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

class CounterTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun counterValue_isShown() {
        composeTestRule.setContent {
            Counter(value = 10, onIncrement = {}, onDecrement = {})
        }
        composeTestRule.onNodeWithText("10").assertIsDisplayed()
    }

    @Test
    fun buttons_areShown() {
        composeTestRule.setContent {
            Counter(value = 10, onIncrement = {}, onDecrement = {})
        }
        composeTestRule.onNodeWithText("+").assertIsDisplayed()
        composeTestRule.onNodeWithText("-").assertIsDisplayed()
    }

    @Test
    fun onIncrementClick_correctMethodIsCalled() {
        val onIncrement: () -> Unit = mockk(relaxed = true)
        composeTestRule.setContent {
            Counter(value = 10, onIncrement = onIncrement, onDecrement = {})
        }
        composeTestRule.onNodeWithText("+").performClick()
        verify { onIncrement.invoke() }
    }

    @Test
    fun onDecrementClick_correctMethodIsCalled() {
        val onDecrement: () -> Unit = mockk(relaxed = true)
        composeTestRule.setContent {
            Counter(value = 10, onIncrement = {}, onDecrement = onDecrement)
        }
        composeTestRule.onNodeWithText("-").performClick()
        verify { onDecrement.invoke() }
    }
}
