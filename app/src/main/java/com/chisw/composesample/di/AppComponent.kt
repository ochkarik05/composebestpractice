package com.chisw.composesample.di

import android.app.Application
import android.content.Context
import com.chisw.common.di.LoggerComponent
import com.chisw.common.di.Singleton
import com.chisw.composesample.App
import com.chisw.data.di.RepositoryComponent
import com.chisw.di.AuthComponent
import com.chisw.domain.di.DomainComponent
import com.chisw.savingstate.di.DateFormatterComponent
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@Suppress("unused")
@Component
@Singleton
abstract class AppComponent(
    @get:Provides val application: Application,
) : LoggerComponent,
    AuthComponent,
    RepositoryComponent,
    DomainComponent,
    DateFormatterComponent {

    companion object {
        fun from(context: Context): AppComponent {
            return (context.applicationContext as App).appComponent
        }
    }
}
