package com.chisw.composesample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.chisw.auth.AuthImpl
import com.chisw.auth.rememberAuthScreenState
import com.chisw.composesample.navigation.AppNavigation
import com.chisw.designsystem.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                AppNavigation(
                    rememberAuthScreenState(
                        auth = AuthImpl,
                        navController = rememberNavController()
                    )
                )
            }
        }
    }
}
