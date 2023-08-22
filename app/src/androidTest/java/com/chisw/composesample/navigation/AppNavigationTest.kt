package com.chisw.composesample.navigation

import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.chisw.auth.Auth
import com.chisw.auth.AuthScreenState
import com.chisw.composesample.MainActivity
import com.chisw.composesample.MainActivityComponent
import com.chisw.composesample.create
import com.chisw.di.LocalMainScreens
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.properties.ReadOnlyProperty
import com.chisw.auth.R as AuthR
import com.chisw.savingstate.R as SavingStateR

@OptIn(ExperimentalCoroutinesApi::class)
class AppNavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    private lateinit var navController: TestNavHostController
    private lateinit var auth: Auth
    private val scope = TestScope()

    private val itemSavingState by composeTestRule.stringResource(SavingStateR.string.saving_state_nav_title)

    private val loginButton by composeTestRule.stringResource(AuthR.string.login_button)
    private val registerButton by composeTestRule.stringResource(AuthR.string.register_button)
    private val forgotPasswordButton by composeTestRule.stringResource(AuthR.string.forgot_password_button)
    private val backToLoginButton by composeTestRule.stringResource(AuthR.string.back_to_login_button)
    private val loginScreenTitle by composeTestRule.stringResource(AuthR.string.login_screen_title)
    private val forgotPasswordScreenTitle by composeTestRule.stringResource(AuthR.string.forgot_password_screen_title)
    private val registrationScreenTitle by composeTestRule.stringResource(AuthR.string.registration_screen_title)

    @Before
    fun setupAppNavHost() {
        auth = object : Auth {

            private val state = MutableStateFlow(false)

            override fun isLoggedIn(): StateFlow<Boolean> = state.asStateFlow()

            override suspend fun login() {
                state.value = true
            }

            override suspend fun logOut() {
                state.value = false
            }
        }
        composeTestRule.activity.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())

            val activityComponent = MainActivityComponent::class.create(composeTestRule.activity)
            CompositionLocalProvider(LocalMainScreens provides activityComponent.screens) {
                AppNavigation(
                    AuthScreenState(
                        auth = auth,
                        scope = scope,
                        navController = navController,
                    ),
                )
            }
        }
    }

    @Test
    fun nonAuthorized_loginIsDisplayed() {
        with(composeTestRule) {
            onNodeWithText(loginScreenTitle).assertIsDisplayed()
        }
    }

    @Test
    fun nonAuthorized_registerClicked_registerIsDisplayed() {
        with(composeTestRule) {
            // GIVEN: User clicks on Register
            onNodeWithText(registerButton).performClick()
            // THEN: Registration screen is displayed
            onNodeWithText(registrationScreenTitle).assertIsDisplayed()
        }
    }

    @Test
    fun nonAuthorized_registerClicked_backstackSizeIs2() {
        with(composeTestRule) {
            // GIVEN: User clicks on Register
            onNodeWithText(registerButton).performClick()
            // THEN: 2 items in backstack
            assertThat(navController.destinations()).hasSize(2)
        }
    }

    @Test
    fun nonAuthorized_forgotPasswordClicked_isDisplayed() {
        with(composeTestRule) {
            // GIVEN: User clicks on Register
            onNodeWithText(forgotPasswordButton).performClick()
            // THEN: Registration screen is displayed
            onNodeWithText(forgotPasswordScreenTitle).assertIsDisplayed()
        }
    }

    @Test
    fun nonAuthorized_forgotPasswordClicked_backstackSizeIs2() {
        with(composeTestRule) {
            // GIVEN: User clicks on Register
            onNodeWithText(forgotPasswordButton).performClick()
            // THEN: 2 items in backstack
            assertThat(navController.destinations()).hasSize(2)
        }
    }

    @Test
    fun nonAuthorized_registerAndBackToLogin_loginIsDisplayed() {
        with(composeTestRule) {
            // GIVEN: User clicks on Register
            onNodeWithText(registerButton).performClick()
            // WHEN: User clicks back on Login
            onNodeWithText(backToLoginButton).performClick()
            // THEN: Login screen is displayed
            onNodeWithText(loginScreenTitle).assertIsDisplayed()
        }
    }

    @Test
    fun nonAuthorized_registerAndBackToLogin_hasOnlyOneBackstackEntry() {
        with(composeTestRule) {
            // GIVEN: User clicks on Register
            onNodeWithText(registerButton).performClick()
            // WHEN: User clicks back on Login
            onNodeWithText(backToLoginButton).performClick()
            // THEN: only one entry in backstack
            assertThat(navController.destinations()).hasSize(1)
        }
    }

    @Test
    fun authorized_MainScreenIsDisplayed() = scope.runTest {
        auth.login()
        advanceUntilIdle()
        composeTestRule.onNodeWithText(itemSavingState).assertIsDisplayed()
    }

    @Test
    fun nonAuthorized_loginClick_MainScreenIsDisplayed() = scope.runTest {
        with(composeTestRule) {
            onNodeWithText(loginButton).performClick()
            advanceUntilIdle()
            onNodeWithText(itemSavingState).assertIsDisplayed()
        }
    }
}

private fun AndroidComposeTestRule<*, *>.stringResource(resId: Int) = ReadOnlyProperty<Any?, String> { _, _ ->
    activity.getString(resId)
}

fun NavHostController.destinations() = currentBackStack.value.mapNotNull { entry ->
    entry.takeIf { it.destination is ComposeNavigator.Destination }
}
