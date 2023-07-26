package com.chisw.designsystem.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    NavigationBar(
        modifier = modifier,
        contentColor = ComposeSampleNavigationDefaults.navigationContentColor(),
        content = content
        )

}

object ComposeSampleNavigationDefaults {
    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.onSurfaceVariant

    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.onTertiary

    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.tertiary

    @Composable
    fun navigationSelectedTextColor() = MaterialTheme.colorScheme.tertiary
}

@Composable
fun RowScope.AppNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    selectedIcon: @Composable () -> Unit = icon,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = ComposeSampleNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = ComposeSampleNavigationDefaults.navigationContentColor(),
            selectedTextColor = ComposeSampleNavigationDefaults.navigationSelectedTextColor(),
            unselectedTextColor = ComposeSampleNavigationDefaults.navigationContentColor(),
            indicatorColor = ComposeSampleNavigationDefaults.navigationIndicatorColor(),
        ),
    )
}
