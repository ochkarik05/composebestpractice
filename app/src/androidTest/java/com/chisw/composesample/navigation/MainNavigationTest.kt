package com.chisw.composesample.navigation

import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.chisw.animation.R
import com.chisw.composesample.MainActivity
import com.chisw.composesample.MainActivityComponent
import com.chisw.composesample.create
import com.chisw.di.LocalMainScreens
import com.chisw.main.MainApp
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.properties.ReadOnlyProperty

class MainNavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val animationNavItem by composeTestRule.stringResource(R.string.animation_nav_title)
    private val customLayoutNavItem by composeTestRule.stringResource(com.chisw.layouts.R.string.layout_nav_title)
    private val savingStateNavItem by composeTestRule.stringResource(
        com.chisw.savingstate.R.string.saving_state_nav_title,
    )

    private val animationScreen by composeTestRule.stringResource(R.string.animation_demo_screen)
    private val customLayoutScreen by composeTestRule.stringResource(
        com.chisw.layouts.R.string.custom_layout_demo_screen,
    )
    private val savingStateScreen by composeTestRule.stringResource(
        com.chisw.savingstate.R.string.saving_state_demo_screen,
    )

    @Before
    fun setUp() {
        composeTestRule.activity.setContent {
            val activityComponent = MainActivityComponent::class.create(composeTestRule.activity)
            CompositionLocalProvider(LocalMainScreens provides activityComponent.screens) {
                MainApp()
            }
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
    fun openLayouts_andPressBack_showsSavingStateScreen() = runTest {
        with(composeTestRule) {
            onNodeWithText(customLayoutNavItem).performClick()
            waitForIdle()
            composeTestRule.activityRule.scenario.onActivity { activity ->
                activity.onBackPressedDispatcher.onBackPressed()
            }
            waitForIdle()
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
