package com.chisw.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.chisw.auth.navigation.AuthRoute
import com.chisw.designsystem.component.ChiButton
import com.chisw.designsystem.component.ChiOutlinedButton

@Composable
fun LoginScreen(authScreenState: AuthScreenState) {
    Surface {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(stringResource(R.string.login_screen_title))
            Spacer(modifier = Modifier.height(128.dp))
            ChiButton(
                onClick = { authScreenState.login() },
                text = { Text(stringResource(R.string.login_button)) }
            )

            ChiOutlinedButton(
                onClick = { authScreenState.navigateTo(AuthRoute.REGISTRATION) },
                text = { Text(stringResource(R.string.register_button)) }
            )

            TextButton(
                onClick = { authScreenState.navigateTo(AuthRoute.FORGOT_PASSWORD) },
                content = { Text(stringResource(R.string.forgot_password_button)) }
            )
        }
    }
}
