package com.chisw.composesample.di

import android.app.Activity
import me.tatarka.inject.annotations.Provides

interface ActivityComponent {
    @get:Provides
    val activity: Activity
}
