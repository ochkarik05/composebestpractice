package com.chisw.di

import androidx.compose.runtime.compositionLocalOf

val LocalMainScreens = compositionLocalOf<MainScreens> {
    throw IllegalStateException("Screens are not provided")
}
