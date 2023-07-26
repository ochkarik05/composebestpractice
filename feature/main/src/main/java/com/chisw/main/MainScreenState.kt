package com.chisw.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.chisw.animation.navigation.navigateToAnimationDemo
import com.chisw.auth.Auth
import com.chisw.layouts.navigation.navigateToCustomLayoutDemo
import com.chisw.navigation.MainScreenDestinations
import com.chisw.savingstate.navigation.navigateToSavingStateDemo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun rememberMainAppState(
    auth: Auth,
    scope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
) = remember(navController) {
    MainScreenState(auth = auth, scope = scope, navController = navController)
}

class MainScreenState(
    private val auth: Auth,
    private val scope: CoroutineScope,
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

    fun logout() {
        scope.launch {
            auth.logOut()
        }
    }
}
