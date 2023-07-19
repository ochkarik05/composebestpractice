package com.chisw.domain.utils

import com.chisw.common.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Suppress("unused")
class Loader(
    private val scope: CoroutineScope,
    private val logger: Logger,
) {

    private val loadingState = ObservableLoadingCounter()
    val observable = loadingState.observable

    fun watchStatus(flow: Flow<InvokeStatus>, debugMessage: String) =
        scope.launch { flow.collectStatus(debugMessage) }

    private suspend fun Flow<InvokeStatus>.collectStatus(debugMessage: String) = collect { status ->
        when (status) {
            InvokeStarted -> loadingState.addLoader()
            InvokeSuccess -> loadingState.removeLoader()
            is InvokeError -> {
                logger.v(status.error, debugMessage )
                loadingState.removeLoader()
            }
        }
    }

    fun addLoader() {
        loadingState.addLoader()
    }

    fun removeLoader() {
        loadingState.removeLoader()
    }
}
