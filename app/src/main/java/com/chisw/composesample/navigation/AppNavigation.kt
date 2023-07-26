package com.chisw.composesample.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.chisw.auth.AuthScreenState
import com.chisw.auth.navigation.auth
import com.chisw.auth.navigation.authRoute
import com.chisw.navigation.main
import com.chisw.navigation.mainRoute

@Composable
fun AppNavigation(
    appAuthScreenState: AuthScreenState,
) {
    val isLoggedIn by appAuthScreenState.auth.isLoggedIn().collectAsStateWithLifecycle()
    redirectByLogin(isLoggedIn, appAuthScreenState.navController)
    NavHost(navController = appAuthScreenState.navController, startDestination = if (isLoggedIn) "main" else "auth") {
        auth(appAuthScreenState)
        main(appAuthScreenState.auth)
    }
}

@SuppressLint("ComposableNaming")
@Composable
private fun redirectByLogin(isLoggedIn: Boolean, navController: NavHostController) {
    LaunchedEffect(key1 = isLoggedIn) {
        if (isLoggedIn) {
            navController.navigate(mainRoute) {
                popUpTo(authRoute) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        } else {
            navController.navigate(authRoute) {
                popUpTo(mainRoute) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }
}
