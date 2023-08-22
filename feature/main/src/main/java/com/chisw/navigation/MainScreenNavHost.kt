package com.chisw.navigation

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.chisw.animation.navigation.animationDemoScreen
import com.chisw.di.LocalMainScreens
import com.chisw.layouts.navigation.customLayoutDemoScreen
import com.chisw.main.MainScreenState
import com.chisw.savingstate.navigation.savingStateDemoRoute
import com.chisw.savingstate.navigation.savingStateDemoScreen

@Composable
fun MainScreenNavHost(
    appState: MainScreenState,
    onShowSnackBar: suspend (String, String, SnackbarDuration) -> Boolean,
    modifier: Modifier = Modifier,
    startDestination: String = savingStateDemoRoute,
) {
    val navController = appState.navController
    val screens = LocalMainScreens.current

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        savingStateDemoScreen { screens.savingStateDemoScreen(onShowSnackBar) }
        animationDemoScreen()
        customLayoutDemoScreen()
    }
}
