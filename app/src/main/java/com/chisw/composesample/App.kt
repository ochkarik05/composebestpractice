package com.chisw.composesample

import android.app.Application
import com.chisw.auth.Auth
import com.chisw.common.AppLogger
import com.chisw.composesample.di.AppComponent
import com.chisw.composesample.di.create

class App : Application() {

    lateinit var appComponent: AppComponent
        private set

    lateinit var logger: AppLogger
        private set

    lateinit var auth: Auth
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = AppComponent::class.create(this)
        logger = appComponent.appLogger
        auth = appComponent.auth
        logger.setup(true)
    }
}
