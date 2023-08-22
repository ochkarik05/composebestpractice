package com.chisw.di

import com.chisw.savingstate.SavingStateDemoScreen
import me.tatarka.inject.annotations.Inject

@Inject
class MainScreens(
    val savingStateDemoScreen: SavingStateDemoScreen,
)
