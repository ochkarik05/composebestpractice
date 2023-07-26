package com.chisw.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.chisw.auth.AuthScreenState
import com.chisw.auth.ForgotPasswordScreen
import com.chisw.auth.LoginScreen
import com.chisw.auth.RegistrationScreen

const val authRoute = "auth"

enum class AuthRoute(val route: String) {
    LOGIN("login"),
    REGISTRATION("registration"),
    FORGOT_PASSWORD("forgot_password"),
}

fun NavHostController.navigate(route: AuthRoute, navOptions: NavOptions?) {
    when (route) {
        AuthRoute.LOGIN -> navigateToLogin(navOptions)
        AuthRoute.REGISTRATION -> navigateToRegistrationScreen(navOptions)
        AuthRoute.FORGOT_PASSWORD -> navigateToForgotPasswordScreen(navOptions)
    }
}

fun NavGraphBuilder.auth(authScreenState: AuthScreenState) {
    navigation(startDestination = AuthRoute.LOGIN.route, route = authRoute) {
        loginScreen(authScreenState)
        registerScreen(authScreenState)
        forgotPasswordScreen(authScreenState)
    }
}

fun NavGraphBuilder.forgotPasswordScreen(authScreenState: AuthScreenState) {
    composable(AuthRoute.FORGOT_PASSWORD.route) {
        ForgotPasswordScreen(authScreenState)
    }
}

fun NavController.navigateToForgotPasswordScreen(navOptions: NavOptions? = null) {
    this.navigate(AuthRoute.FORGOT_PASSWORD.route, navOptions)
}

fun NavGraphBuilder.registerScreen(authScreenState: AuthScreenState) {
    composable(AuthRoute.REGISTRATION.route) {
        RegistrationScreen(authScreenState)
    }
}

fun NavController.navigateToRegistrationScreen(navOptions: NavOptions? = null) {
    this.navigate(AuthRoute.REGISTRATION.route, navOptions)
}

fun NavGraphBuilder.loginScreen(auth: AuthScreenState) {
    composable(AuthRoute.LOGIN.route) {
        LoginScreen(auth)
    }
}

fun NavController.navigateToLogin(navOptions: NavOptions? = null) {
    this.navigate(AuthRoute.LOGIN.route, navOptions)
}
