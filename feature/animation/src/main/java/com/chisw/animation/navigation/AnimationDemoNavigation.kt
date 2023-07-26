@file:Suppress("unused")

package com.chisw.animation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.chisw.animation.AnimationDemoScreen

const val animationDemoRoute = "animation_demo_route"

fun NavController.navigateToAnimationDemo(navOptions: NavOptions? = null) {
    this.navigate(animationDemoRoute, navOptions)
}

fun NavGraphBuilder.animationDemoScreen() {
    composable(route = animationDemoRoute) {
        AnimationDemoScreen()
    }
}
