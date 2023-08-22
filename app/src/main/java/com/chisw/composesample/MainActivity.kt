package com.chisw.composesample

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.rememberNavController
import com.chisw.auth.rememberAuthScreenState
import com.chisw.common.di.ActivityScope
import com.chisw.composesample.di.ActivityComponent
import com.chisw.composesample.di.AppComponent
import com.chisw.composesample.navigation.AppNavigation
import com.chisw.designsystem.theme.AppTheme
import com.chisw.di.LocalMainScreens
import com.chisw.di.MainScreens
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityComponent = MainActivityComponent::class.create(this)
        val auth = (application as App).auth
        setContent {
            AppTheme {
                CompositionLocalProvider(LocalMainScreens provides activityComponent.screens) {
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
}

@Component
@ActivityScope
abstract class MainActivityComponent(
    @get:Provides override val activity: Activity,
    @Component val appComponent: AppComponent = AppComponent.from(activity),
) : ActivityComponent {
    abstract val screens: MainScreens
}
