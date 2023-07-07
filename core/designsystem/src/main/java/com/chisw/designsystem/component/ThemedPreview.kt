package com.chisw.designsystem.component

import androidx.compose.runtime.Composable
import com.chisw.designsystem.theme.AppTheme

@Composable
fun ThemedPreview(content: @Composable () -> Unit) {
        AppTheme {
            content()
    }
}
