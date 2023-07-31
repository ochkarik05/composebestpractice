@file:Suppress("unused")

package com.chisw.layouts.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.chisw.layouts.CustomLayoutDemoScreen

const val customLayoutDemoRoute = "custom_layout_demo_route"

fun NavController.navigateToCustomLayoutDemo(navOptions: NavOptions? = null) {
    this.navigate(customLayoutDemoRoute, navOptions)
}

fun NavGraphBuilder.customLayoutDemoScreen() {
    composable(route = customLayoutDemoRoute) {
        CustomLayoutDemoScreen()
    }
}
