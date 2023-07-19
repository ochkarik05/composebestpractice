package com.chisw.domain.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Suppress("unused")
class AppCoroutineDispatchers(
    val io: CoroutineDispatcher = Dispatchers.IO,
    val main: CoroutineDispatcher = Dispatchers.Main,
    val default: CoroutineDispatcher = Dispatchers.Default,
)
