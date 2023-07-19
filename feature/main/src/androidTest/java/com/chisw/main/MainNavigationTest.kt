package com.chisw.main

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.properties.ReadOnlyProperty
import com.chisw.animation.R as animationR
import com.chisw.layouts.R as layoutsR
import com.chisw.savingstate.R as savingStateR

class MainNavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val animationNavItem by composeTestRule.stringResource(animationR.string.animation_nav_title)
    private val customLayoutNavItem by composeTestRule.stringResource(layoutsR.string.layout_nav_title)
    private val savingStateNavItem by composeTestRule.stringResource(savingStateR.string.saving_state_nav_title)

    private val animationScreen by composeTestRule.stringResource(animationR.string.animation_demo_screen)
    private val customLayoutScreen by composeTestRule.stringResource(layoutsR.string.custom_layout_demo_screen)
    private val savingStateScreen by composeTestRule.stringResource(savingStateR.string.saving_state_demo_screen)

    @Before
    fun setUp() {
        composeTestRule.setContent {
            MainApp()
        }
    }

    @Test
    fun onStart_savingStateScreenIsShown() {
        with(composeTestRule) {
            onNodeWithText(savingStateScreen).assertIsDisplayed()
            onNodeWithText(savingStateNavItem).assertIsSelected()
        }
    }

    @Test
    fun whenAnimationIsSelected_animationScreenIsShown() {
        with(composeTestRule) {
            onNodeWithText(animationNavItem).performClick()
            onNodeWithText(animationScreen).assertIsDisplayed()
            onNodeWithText(animationNavItem).assertIsSelected()
        }
    }

    @Test
    fun whenLayoutsSelected_layoutScreenIsShown() {
        with(composeTestRule) {
            onNodeWithText(customLayoutNavItem).performClick()
            onNodeWithText(customLayoutScreen).assertIsDisplayed()
            onNodeWithText(customLayoutNavItem).assertIsSelected()
        }
    }

    @Test
    fun openLayouts_andPressBack_showsSavingStateScreen() {
        with(composeTestRule) {
            onNodeWithText(customLayoutNavItem).performClick()
            composeTestRule.activityRule.scenario.onActivity { activity ->
                activity.onBackPressedDispatcher.onBackPressed()
            }

            onNodeWithText(savingStateScreen).assertIsDisplayed()
        }
    }

    @Test
    fun openLayouts_andPressSavingStateItem_showsSavingStateScreen() {
        with(composeTestRule) {
            onNodeWithText(customLayoutNavItem).performClick()
            onNodeWithText(savingStateNavItem).performClick()
            onNodeWithText(savingStateScreen).assertIsDisplayed()
            onNodeWithText(savingStateNavItem).assertIsSelected()
        }
    }
}

private fun AndroidComposeTestRule<*, *>.stringResource(resId: Int) = ReadOnlyProperty<Any?, String> { _, _ ->
    activity.getString(resId)
}
