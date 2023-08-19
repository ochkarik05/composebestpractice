package com.chisw.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.chisw.auth.navigation.AuthRoute
import com.chisw.auth.navigation.navigate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun rememberAuthScreenState(
    auth: Auth,
    scope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): AuthScreenState = remember(auth, scope, navController) {
    AuthScreenState(auth = auth, scope = scope, navController = navController)
}

class AuthScreenState(
    val scope: CoroutineScope,
    val auth: Auth,
    val navController: NavHostController,
) {
    fun navigateTo(route: AuthRoute) {
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        navController.navigate(route, navOptions)
    }

    fun login() {
        scope.launch {
            auth.login()
        }
    }
}
