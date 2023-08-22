package com.chisw.savingstate

import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chisw.common.Logger
import com.chisw.common.combine
import com.chisw.data.model.Profile
import com.chisw.domain.auth.LogoutInteractor
import com.chisw.domain.profile.ObserveProfileInteractor
import com.chisw.domain.profile.SaveProfileInteractor
import com.chisw.domain.time.ObserveCurrentTimeInteractor
import com.chisw.domain.utils.InvokeError
import com.chisw.domain.utils.Loader
import com.chisw.savingstate.di.DateFormatter
import com.chisw.savingstate.di.TimeFormatter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import java.util.Date

const val INITIAL_STEP = 0

sealed interface SavingStateEvent {
    data object Logout : SavingStateEvent
    data object Increment : SavingStateEvent
    data object Decrement : SavingStateEvent
    data object ClearErrors : SavingStateEvent
    class SaveProfile(val profile: Profile) : SavingStateEvent
}

@Stable
data class SavingStateDemoViewState(
    val currentStep: Int = 0,
    val currentTime: String = "",
    val currentDate: String = "",
    val profile: Profile = Profile("", ""),
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val eventSink: (SavingStateEvent) -> Unit,
)

internal const val CURRENT_STEP = "currentStep"

@Suppress("LongParameterList")
@Inject
class SavingStateDemoViewModel(
    @Assisted private val savedStateHandle: SavedStateHandle,
    currentTimeInteractor: ObserveCurrentTimeInteractor,
    logoutInteractor: LogoutInteractor,
    saveProfileInteractor: SaveProfileInteractor,
    observeProfileInteractor: ObserveProfileInteractor,
    logger: Logger,
    private val timeFormatter: TimeFormatter,
    private val dateFormatter: DateFormatter,
) : ViewModel() {

    private val loadingState by lazy { Loader(viewModelScope, logger) }

    private val errorSink by lazy { MutableStateFlow<Throwable?>(null) }

    private val eventSink: (SavingStateEvent) -> Unit = { event ->
        when (event) {
            SavingStateEvent.Logout -> {
                viewModelScope.launch {
                    logoutInteractor()
                }
            }

            SavingStateEvent.Decrement -> decrementCounter()
            SavingStateEvent.Increment -> incrementCounter()
            is SavingStateEvent.SaveProfile -> viewModelScope.launch {
                loadingState.watchStatus(
                    saveProfileInteractor(SaveProfileInteractor.Params(event.profile)).onEach {
                        if (it is InvokeError) errorSink.value = (it.error)
                    },
                    "Watch saving profile status",
                )
            }

            SavingStateEvent.ClearErrors -> errorSink.value = null
        }
    }

    val state by lazy {
        combine(
            savedStateHandle.getStateFlow(CURRENT_STEP, INITIAL_STEP),
            currentTimeInteractor(
                ObserveCurrentTimeInteractor.Params {
                    timeFormatter.format(Date(it))
                },
            ),

            currentTimeInteractor(
                ObserveCurrentTimeInteractor.Params {
                    dateFormatter.format(Date(it))
                },
            ),

            observeProfileInteractor(),
            loadingState.observable,
            errorSink,
        ) { step, time, date, profile, isLoading, error ->
            SavingStateDemoViewState(
                currentStep = step,
                currentTime = time,
                currentDate = date,
                isLoading = isLoading,
                profile = profile,
                error = error,
                eventSink = eventSink,
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = SavingStateDemoViewState(
                eventSink = eventSink,
                isLoading = true,
            ),
        )
    }

    private fun updateCounter(step: Int) {
        savedStateHandle[CURRENT_STEP] = step
    }

    private fun incrementCounter() {
        updateCounter(state.value.currentStep + 1)
    }

    private fun decrementCounter() {
        updateCounter(state.value.currentStep - 1)
    }
}
