package com.chisw.savingstate.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Named

private const val TIME_FORMAT = "HH:mm:ss"
private const val DATE_FORMAT = "EEE, d MMM yyyy"

const val DATE_FORMATTER = "date_formatter"
const val TIME_FORMATTER = "time_formatter"

@Module
@InstallIn(ViewModelComponent::class)
class DateFormatterModule {
    @Provides
    @Named(DATE_FORMATTER)
    fun dateFormatter() = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())

    @Provides
    @Named(TIME_FORMATTER)
    fun timeFormatter() = SimpleDateFormat(TIME_FORMAT, Locale.getDefault())
}
