package com.chisw.designsystem.component

import androidx.annotation.VisibleForTesting
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chisw.data.model.Profile
import com.chisw.designsystem.R

@Composable
fun rememberProfileState(profile: Profile = Profile()): ProfileState {
    return rememberSaveable(profile, saver = ProfileState.Saver) {
        ProfileState(
            firstNameState = mutableStateOf(profile.firstName),
            lastNameState = mutableStateOf(profile.lastName),
        )
    }
}

class ProfileState(
    private val firstNameState: MutableState<String>,
    private var lastNameState: MutableState<String>,
) {
    var firstName by firstNameState
    var lastName by lastNameState

    val profile: Profile
        get() = Profile(firstName, lastName)

    companion object {
        val Saver = run {
            mapSaver(
                save = { profile: ProfileState ->
                    mapOf(
                        profile::firstNameState.name to profile.firstNameState.value,
                        profile::lastNameState.name to profile.lastNameState.value,
                    )
                },
                restore = {
                    val firstNameState: String by it
                    val lastNameState: String by it
                    ProfileState(
                        firstNameState = mutableStateOf(firstNameState),
                        lastNameState = mutableStateOf(lastNameState),
                    )
                },
            )
        }
    }
}

@VisibleForTesting
const val ALPHA_MIN = 0.3f

@VisibleForTesting
const val ALPHA_MAX = 0.9f

@VisibleForTesting
const val ANIM_DURATION = 2600

@VisibleForTesting
const val PROFILE_VIEW_TEST_TAG = "ProfileView"

@Composable
fun ProfileView(
    initialProfile: Profile,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    onUpdate: (Profile) -> Unit,
    onLogout: () -> Unit,
) {
    val profileState = rememberProfileState(initialProfile)

    val alpha: State<Float> = rememberAnimatedAlpha(isLoading)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .alpha(alpha.value)
            .semantics {
                alphaValue = alpha.value
            }
            .testTag(PROFILE_VIEW_TEST_TAG),
        verticalArrangement = spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = stringResource(R.string.profile_view_title), textAlign = TextAlign.Center)
        ProfileEditor(
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth(),
            profileState = profileState,
        )
        ChiButton(
            onClick = { onUpdate(profileState.profile) },
            enabled = (initialProfile != profileState.profile) && !isLoading,
        ) {
            Text(stringResource(R.string.save))
        }
        ChiButton(
            onClick = onLogout,
            enabled = !isLoading,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            content = {
                Text(stringResource(R.string.logout))
            },
        )
    }
}

@Composable
private fun rememberAnimatedAlpha(isLoading: Boolean) = if (isLoading) {
    val infiniteTransition = rememberInfiniteTransition(label = "Alpha Animation")
    infiniteTransition.animateFloat(
        ALPHA_MAX,
        ALPHA_MIN,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = ANIM_DURATION),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "",
    )
} else {
    remember {
        mutableStateOf(1f)
    }
}

@Composable
fun ProfileEditor(
    enabled: Boolean,
    modifier: Modifier = Modifier,
    profileState: ProfileState = rememberProfileState(),
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = spacedBy(16.dp),
    ) {
        val firstNameLabel = stringResource(R.string.firstname)
        val lastNameLabel = stringResource(R.string.lastname)
        OutlinedTextField(
            enabled = enabled,
            value = profileState.firstName,
            label = { Text(firstNameLabel) },
            onValueChange = {
                profileState.firstName = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .semantics { contentDescription = firstNameLabel },
        )

        OutlinedTextField(
            enabled = enabled,
            value = profileState.lastName,
            label = { Text(stringResource(R.string.lastname)) },
            onValueChange = { profileState.lastName = it },
            modifier = Modifier
                .fillMaxWidth()
                .semantics { contentDescription = lastNameLabel },
        )
    }
}

@Preview
@Composable
private fun Preview() {
    Themed3Preview {
        ProfileView(
            initialProfile = Profile("James", "Bond"),
            isLoading = true,
            onUpdate = {},
            onLogout = {},
        )
    }
}

val AlphaKey = SemanticsPropertyKey<Float>("DayStatusKey")
var SemanticsPropertyReceiver.alphaValue by AlphaKey
