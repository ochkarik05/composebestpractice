package com.chisw.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.chisw.designsystem.component.AppNavigationBarItem
import com.chisw.designsystem.component.BottomNavBar
import com.chisw.navigation.MainScreenDestinations
import com.chisw.navigation.MainScreenNavHost

@Composable
fun MainApp(
    appState: MainScreenState = rememberMainAppState(),
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        bottomBar = {
            ComposeSampleBottomBar(
                destinations = appState.mainScreenDestinations,
                onNavigateToDestination = appState::navigateToMainDestination,
                currentDestination = appState.currentDestination,
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { paddingValues ->

        Box(
            Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            MainScreenNavHost(appState = appState, onShowSnackBar = { message, actionLabel, duration ->
                snackbarHostState.showSnackbar(
                    message = message,
                    actionLabel = actionLabel,
                    duration = duration,
                ) == SnackbarResult.ActionPerformed
            })
        }
    }
}

@Composable
private fun ComposeSampleBottomBar(
    destinations: List<MainScreenDestinations>,
    onNavigateToDestination: (MainScreenDestinations) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    BottomNavBar(
        modifier = modifier,
    ) {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            AppNavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        imageVector = destination.unselectedIcon,
                        contentDescription = null,
                    )
                },
                selectedIcon = {
                    Icon(
                        imageVector = destination.selectedIcon,
                        contentDescription = null,
                    )
                },
                label = { Text(stringResource(destination.iconTextId)) },
            )
        }
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: MainScreenDestinations) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false
