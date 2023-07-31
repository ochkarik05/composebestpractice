package com.chisw.composesample

import android.app.Application
import com.chisw.common.AppLogger
import com.chisw.common.defaultLogger

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        (defaultLogger as? AppLogger)?.setup(true)
    }
}
