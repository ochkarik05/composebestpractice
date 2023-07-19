package com.chisw.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.chisw.animation.navigation.navigateToAnimationDemo
import com.chisw.layouts.navigation.navigateToCustomLayoutDemo
import com.chisw.navigation.MainScreenDestinations
import com.chisw.savingstate.navigation.navigateToSavingStateDemo

@Composable
fun rememberMainAppState(
    navController: NavHostController = rememberNavController(),
) = remember(navController) {
    MainScreenState(navController = navController)
}

class MainScreenState(
    val navController: NavHostController,
) {

    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val mainScreenDestinations: List<MainScreenDestinations> = MainScreenDestinations.values().asList()

    fun navigateToMainDestination(mainScreenDestinations: MainScreenDestinations) {
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (mainScreenDestinations) {
            MainScreenDestinations.SAVING_STATE_DEMO -> navController.navigateToSavingStateDemo(navOptions)
            MainScreenDestinations.ANIMATION_DEMO -> navController.navigateToAnimationDemo(navOptions)
            MainScreenDestinations.LAYOUT_DEMO -> navController.navigateToCustomLayoutDemo(navOptions)
        }
    }
}
