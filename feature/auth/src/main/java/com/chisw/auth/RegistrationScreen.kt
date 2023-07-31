package com.chisw.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.chisw.auth.navigation.AuthRoute
import com.chisw.designsystem.component.ChiButton
import com.chisw.designsystem.component.ChiOutlinedButton

@Composable
fun RegistrationScreen(authScreenState: AuthScreenState) {
    Surface {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(stringResource(R.string.registration_screen_title))
            Spacer(modifier = Modifier.height(128.dp))
            ChiButton(
                onClick = { authScreenState.login() },
                content = { Text(stringResource(id = R.string.register_button)) }
            )

            ChiOutlinedButton(
                onClick = { authScreenState.navigateTo(AuthRoute.LOGIN) },
                content = { Text(stringResource(id = R.string.back_to_login_button)) }
            )
        }
    }
}
