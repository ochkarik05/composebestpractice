@file:Suppress("unused")

package com.chisw.savingstate.navigation

import androidx.compose.material3.SnackbarDuration
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.chisw.savingstate.SavingStateDemoScreen

const val savingStateDemoRoute = "saving_state_demo_route"

fun NavController.navigateToSavingStateDemo(navOptions: NavOptions? = null) {
    this.navigate(savingStateDemoRoute, navOptions)
}

fun NavGraphBuilder.savingStateDemoScreen(onShowSnackBar: suspend (String, String, SnackbarDuration) -> Boolean) {
    composable(route = savingStateDemoRoute) {
        SavingStateDemoScreen(onShowSnackBar)
    }
}
