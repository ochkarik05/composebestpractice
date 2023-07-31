package com.chisw.savingstate

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.chisw.common.Logger
import com.chisw.common.defaultLogger
import com.chisw.domain.auth.LogoutInteractor
import com.chisw.domain.profile.ObserveProfileInteractor
import com.chisw.domain.profile.SaveProfileInteractor
import com.chisw.domain.time.ObserveCurrentTimeInteractor

// TODO: Migrate to Hilt
@Suppress("UNCHECKED_CAST")
class SavingStateDemoViewModelFactory(
    private val currentTimeInteractor: ObserveCurrentTimeInteractor = ObserveCurrentTimeInteractor(),
    private val logoutInteractor: LogoutInteractor = LogoutInteractor(),
    private val saveProfileInteractor: SaveProfileInteractor = SaveProfileInteractor(),
    private val observeProfileInteractor: ObserveProfileInteractor = ObserveProfileInteractor(),
    private val logger: Logger = defaultLogger,
) : AbstractSavedStateViewModelFactory() {
    override fun <T : ViewModel> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
        return SavingStateDemoViewModel(
            savedStateHandle = handle,
            currentTimeInteractor = currentTimeInteractor,
            logoutInteractor = logoutInteractor,
            saveProfileInteractor = saveProfileInteractor,
            observeProfileInteractor = observeProfileInteractor,
            logger = logger,
        ) as T
    }
}
