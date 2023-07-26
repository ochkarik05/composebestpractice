package com.chisw.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.chisw.auth.Auth
import com.chisw.main.MainApp

const val mainRoute = "main"
private const val dummyRoute = "dummy"

fun NavGraphBuilder.main(auth: Auth) {
    navigation(startDestination = dummyRoute, route = mainRoute) {
        composable(route = dummyRoute) {
            MainApp(auth)
        }
    }
}
