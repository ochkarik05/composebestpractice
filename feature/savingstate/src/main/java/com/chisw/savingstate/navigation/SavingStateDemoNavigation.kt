@file:Suppress("unused")

package com.chisw.savingstate.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.chisw.common.di.LoggerComponent
import com.chisw.di.AuthComponent

const val savingStateDemoRoute = "saving_state_demo_route"

interface AuthComponentProvider {
    fun authComponent(): AuthComponent
}

interface LoggerComponentProvider {
    fun loggerComponent(): LoggerComponent
}

fun NavController.navigateToSavingStateDemo(navOptions: NavOptions? = null) {
    this.navigate(savingStateDemoRoute, navOptions)
}

fun NavGraphBuilder.savingStateDemoScreen(screen: @Composable () -> Unit) {
    composable(route = savingStateDemoRoute) {
        screen()
    }
}
