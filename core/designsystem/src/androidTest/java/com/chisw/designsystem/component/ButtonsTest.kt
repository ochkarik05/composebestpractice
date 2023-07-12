package com.chisw.designsystem.component

import androidx.activity.ComponentActivity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import org.junit.Rule
import org.junit.Test

class ButtonsTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun whenButtonHasText_itIsVisible() {

        val text = "Button"
        composeTestRule.setContent {
            ChiButton(
                onClick = {},
                text = {
                    Text(text)
                }
            )
        }
        composeTestRule.onNodeWithText(text).assertIsDisplayed()
    }

    @Test
    fun chiButton_hasClickActionAssigned() {

        val text = "Button"
        composeTestRule.setContent {
            ChiButton(
                modifier = Modifier.semantics { contentDescription = text },
                onClick = {},
                text = {}
            )
        }
        composeTestRule.onNodeWithContentDescription(text).assertHasClickAction()
    }

    @Test
    fun when_chiButton_hasLeadingIcon_itIsVisible() {

        val text = "Button Image"
        composeTestRule.setContent {
            ChiButton(
                onClick = {},
                text = {},
                leadingIcon = { Icon(Icons.Default.Add, contentDescription = text) }
            )
        }
        composeTestRule.onRoot(useUnmergedTree = true).printToLog("TAG")
        composeTestRule.onNodeWithContentDescription(text).assertIsDisplayed()
    }

}
