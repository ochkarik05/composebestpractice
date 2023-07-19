package com.chisw.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import androidx.navigation.testing.TestNavHostController
import com.chisw.animation.navigation.animationDemoRoute
import com.chisw.layouts.navigation.customLayoutDemoRoute
import com.chisw.navigation.MainScreenDestinations
import com.chisw.savingstate.navigation.savingStateDemoRoute
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test

class MainScreenStateTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var state: MainScreenState

    @Test
    fun testSetupCorrectly() {
        assertThat(true).isTrue()
    }

    @Test
    fun mainScreenState_destinations() {
        composeTestRule.setContent {
            state = rememberMainAppState()
        }
        assertThat(state.mainScreenDestinations).hasSize(3)
        assertThat(state.mainScreenDestinations[0]).isEqualTo(MainScreenDestinations.SAVING_STATE_DEMO)
        assertThat(state.mainScreenDestinations[1]).isEqualTo(MainScreenDestinations.ANIMATION_DEMO)
        assertThat(state.mainScreenDestinations[2]).isEqualTo(MainScreenDestinations.LAYOUT_DEMO)
    }

    @Test
    fun mainScreenState_navigate() {
        var currentDestination: String? = null
        composeTestRule.setContent {
            val navController = rememberTestNavController()
            state = rememberMainAppState(navController = navController)

            currentDestination = state.currentDestination?.route

            LaunchedEffect(key1 = Unit) {
                state.navigateToMainDestination(MainScreenDestinations.ANIMATION_DEMO)
            }
        }

        assertThat(currentDestination).isEqualTo(animationDemoRoute)
    }
}

@Composable
private fun rememberTestNavController(): TestNavHostController {
    val context = LocalContext.current
    val navController = remember {
        TestNavHostController(context).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
            graph = createGraph(startDestination = savingStateDemoRoute) {
                composable(savingStateDemoRoute) { }
                composable(animationDemoRoute) { }
                composable(customLayoutDemoRoute) { }
            }
        }
    }
    return navController
}
