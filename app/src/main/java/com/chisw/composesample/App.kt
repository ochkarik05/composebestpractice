package com.chisw.composesample

import android.app.Application
import com.chisw.common.AppLogger
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var logger: AppLogger

    override fun onCreate() {
        super.onCreate()
        logger.setup(true)
    }
}
