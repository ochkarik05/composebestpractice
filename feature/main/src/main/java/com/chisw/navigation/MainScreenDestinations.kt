@file:Suppress("unused")

package com.chisw.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.chisw.designsystem.icon.AppIcons
import com.chisw.animation.R as animationR
import com.chisw.layouts.R as layoutsR
import com.chisw.savingstate.R as savingStateR

enum class MainScreenDestinations(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int,
    val titleTextId: Int,
) {
    SAVING_STATE_DEMO(
        selectedIcon = AppIcons.SavingSateIconSelected,
        unselectedIcon = AppIcons.SavingStateIcon,
        iconTextId = savingStateR.string.saving_state_nav_title,
        titleTextId = savingStateR.string.saving_state_demo_screen,
    ),
    ANIMATION_DEMO(
        selectedIcon = AppIcons.AnimationIconSelected,
        unselectedIcon = AppIcons.AnimationIcon,
        iconTextId = animationR.string.animation_nav_title,
        titleTextId = animationR.string.animation_demo_screen,

    ),
    LAYOUT_DEMO(
        selectedIcon = AppIcons.LayoutIconSelected,
        unselectedIcon = AppIcons.LayoutIcon,
        iconTextId = layoutsR.string.layout_nav_title,
        titleTextId = layoutsR.string.custom_layout_demo_screen,

    )
}
