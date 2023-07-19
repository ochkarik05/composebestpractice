package com.chisw.domain.utils

sealed interface InvokeStatus
object InvokeStarted : InvokeStatus
object InvokeSuccess : InvokeStatus
class InvokeError(val error: Throwable) : InvokeStatus
