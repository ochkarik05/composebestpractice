package com.chisw.savingstate

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.chisw.designsystem.component.ChiButton
import kotlinx.coroutines.launch

@Composable
fun SavingStateDemoScreen(onShowSnackBar: suspend (String, String) -> Boolean) {
    val scope = rememberCoroutineScope()
    Box(Modifier.fillMaxSize()) {
        Text(stringResource(R.string.saving_state_demo_screen))

        ChiButton(
            onClick = {
                scope.launch {
                    onShowSnackBar("Confirm logout: ", "OK")
                }
            },
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(stringResource(R.string.logout))
        }

    }
}
