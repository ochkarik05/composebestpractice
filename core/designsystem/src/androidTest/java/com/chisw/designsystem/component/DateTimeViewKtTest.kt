package com.chisw.designsystem.component

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class DateTimeViewKtTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private val time = "Time"
    private val date = "Date"

    @Test
    fun currentTime_isDisplayed() {
        composeTestRule.setContent {
            DateTimeView(date = date, time = time)
        }
        composeTestRule.onNodeWithText(time).assertIsDisplayed()
    }

    @Test
    fun currentDate_isNotDisplayed() {
        composeTestRule.setContent {
            DateTimeView(date = date, time = time)
        }
        composeTestRule.onNodeWithText(date).assertDoesNotExist()
    }

    @Test
    fun whenClicked_currentDate_isDisplayed() {
        composeTestRule.setContent {
            DateTimeView(date = date, time = time)
        }
        composeTestRule.onNodeWithText(time).onParent().performClick()
        composeTestRule.onNodeWithText(date).assertIsDisplayed()
    }

    @Test
    fun stateIsRestoredCorrectly() {
        val tester = StateRestorationTester(composeTestRule)

        tester.setContent {
            DateTimeView(date = date, time = time)
        }
        composeTestRule.onNodeWithText(time).onParent().performClick()
        tester.emulateSavedInstanceStateRestore()
        composeTestRule.onNodeWithText(date).assertIsDisplayed()
    }
}
