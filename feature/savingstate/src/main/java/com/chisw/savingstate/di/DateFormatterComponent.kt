package com.chisw.savingstate.di

import me.tatarka.inject.annotations.Provides
import java.text.SimpleDateFormat
import java.util.Locale

private const val TIME_FORMAT = "HH:mm:ss"
private const val DATE_FORMAT = "EEE, d MMM yyyy"

typealias DateFormatter = SimpleDateFormat
typealias TimeFormatter = SimpleDateFormat

interface DateFormatterComponent {
    @Provides
    fun dateFormatter(): DateFormatter = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())

    @Provides
    fun timeFormatter(): TimeFormatter = SimpleDateFormat(TIME_FORMAT, Locale.getDefault())
}
