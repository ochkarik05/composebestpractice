package com.chisw.composesample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.chisw.auth.Auth
import com.chisw.auth.rememberAuthScreenState
import com.chisw.composesample.navigation.AppNavigation
import com.chisw.designsystem.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var auth: Auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                AppNavigation(
                    rememberAuthScreenState(
                        auth = auth,
                        navController = rememberNavController(),
                    ),
                )
            }
        }
    }
}
