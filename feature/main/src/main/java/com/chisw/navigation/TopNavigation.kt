package com.chisw.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.chisw.main.MainApp

const val mainRoute = "main"
private const val dummyRoute = "dummy"

fun NavGraphBuilder.main() {
    navigation(startDestination = dummyRoute, route = mainRoute) {
        composable(route = dummyRoute) {
            MainApp()
        }
    }
}
