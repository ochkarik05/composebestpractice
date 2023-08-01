package com.chisw.savingstate

import android.content.res.Configuration
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chisw.data.model.Profile
import com.chisw.designsystem.component.ChiHeader
import com.chisw.designsystem.component.Counter
import com.chisw.designsystem.component.DateTimeView
import com.chisw.designsystem.component.ProfileView
import com.chisw.designsystem.component.Themed3Preview
import kotlinx.coroutines.launch

@VisibleForTesting
const val SAVING_STATE_DEMO_SCREEN_TAG = "saving_state_demo_screen_tag"

@Composable
fun SavingStateDemoScreen(onShowSnackBar: suspend (String, String, SnackbarDuration) -> Boolean) {
    val viewModel: SavingStateDemoViewModel = hiltViewModel()
    SavingStateDemoScreen(viewModel, onShowSnackBar)
}

@VisibleForTesting
@Composable
fun SavingStateDemoScreen(
    viewModel: SavingStateDemoViewModel,
    onShowSnackBar: suspend (String, String, SnackbarDuration) -> Boolean,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val error = remember(state.error) {
        state.error
    }

    LaunchedEffect(key1 = error) {
        if (error != null) {
            val result = onShowSnackBar(error.message ?: "Unknown Error", "OK", SnackbarDuration.Indefinite)
            if (result) {
                state.eventSink(SavingStateEvent.ClearErrors)
            }
        }
    }

    val scope = rememberCoroutineScope()

    val confirmLogout = stringResource(R.string.confirm_logout)

    val onLogoutClick = remember(scope, onShowSnackBar) {
        {
            scope.launch {
                val result = onShowSnackBar(confirmLogout, "OK", SnackbarDuration.Short)
                if (result) {
                    state.eventSink(SavingStateEvent.Logout)
                }
            }
            Unit
        }
    }

    val onSaveProfile = remember(scope, onShowSnackBar) {
        { profile: Profile ->
            state.eventSink(SavingStateEvent.SaveProfile(profile))
        }
    }

    val onIncrement = remember(state.eventSink) {
        { state.eventSink(SavingStateEvent.Increment) }
    }

    val onDecrement = remember(state.eventSink) {
        { state.eventSink(SavingStateEvent.Decrement) }
    }

    SavingStateDemoScreen(
        state = state,
        onIncrement = onIncrement,
        onDecrement = onDecrement,
        onLogout = onLogoutClick,
        onSaveProfile = onSaveProfile,
    )
}

@Composable
private fun SavingStateDemoScreen(
    state: SavingStateDemoViewState,
    onLogout: () -> Unit,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onSaveProfile: (Profile) -> Unit,
) {
    Surface {
        val currentStep = remember(state.currentStep) {
            state.currentStep
        }

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .testTag(SAVING_STATE_DEMO_SCREEN_TAG),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            ChiHeader(
                text = stringResource(R.string.saving_state_demo_screen),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            )

            Card(
                Modifier.padding(horizontal = 16.dp),
            ) {
                Counter(
                    value = currentStep,
                    onIncrement = onIncrement,
                    onDecrement = onDecrement,
                    modifier = Modifier.padding(16.dp),
                )
            }

            Card(Modifier.padding(horizontal = 16.dp)) {
                DateTimeView(
                    date = state.currentDate,
                    time = state.currentTime,
                    modifier = Modifier.padding(16.dp),
                )
            }

            Card(Modifier.padding(horizontal = 16.dp)) {
                ProfileView(
                    initialProfile = state.profile,
                    onUpdate = onSaveProfile,
                    isLoading = state.isLoading,
                    onLogout = onLogout,
                    modifier = Modifier.padding(16.dp),
                )
            }

            Spacer(Modifier.heightIn(16.dp))
        }
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun Preview() {
    Themed3Preview {
        SavingStateDemoScreen(
            state = SavingStateDemoViewState(0, "8:00", "Wed, 4 Jul 2001") {},
            {},
            {},
            {},
            {},
        )
    }
}
