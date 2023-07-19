package com.chisw.common

import android.annotation.SuppressLint
import android.util.Log
import timber.log.Timber
import java.util.regex.Pattern

interface Logger {

    /** Log a verbose message with optional format args.  */
    fun v(message: String, vararg args: Any?)

    /** Log a verbose exception and a message with optional format args.  */
    fun v(t: Throwable, message: String, vararg args: Any?)

    /** Log a verbose exception.  */
    fun v(t: Throwable)

    /** Log a debug message with optional format args.  */
    fun d(message: String, vararg args: Any?)

    /** Log a debug exception and a message with optional format args.  */
    fun d(t: Throwable, message: String, vararg args: Any?)

    /** Log a debug exception.  */
    fun d(t: Throwable)

    /** Log an info message with optional format args.  */
    fun i(message: String, vararg args: Any?)

    /** Log an info exception and a message with optional format args.  */
    fun i(t: Throwable, message: String, vararg args: Any?)

    /** Log an info exception.  */
    fun i(t: Throwable)

    /** Log a warning message with optional format args.  */
    fun w(message: String, vararg args: Any?)

    /** Log a warning exception and a message with optional format args.  */
    fun w(t: Throwable, message: String, vararg args: Any?)

    /** Log a warning exception.  */
    fun w(t: Throwable)

    /** Log an error message with optional format args.  */
    fun e(message: String, vararg args: Any?)

    /** Log an error exception and a message with optional format args.  */
    fun e(t: Throwable, message: String, vararg args: Any?)

    /** Log an error exception.  */
    fun e(t: Throwable)

    /** Log an assert message with optional format args.  */
    fun wtf(message: String, vararg args: Any?)

    /** Log an assert exception and a message with optional format args.  */
    fun wtf(t: Throwable, message: String, vararg args: Any?)

    /** Log an assert exception.  */
    fun wtf(t: Throwable)
}

class AppLogger : Logger {

    fun setup(debugMode: Boolean) {
        if (debugMode) {
            Timber.plant(AppDebugTree())
        }
        Timber.plant(
            CrashlyticsTree(
//                firebaseCrashlytics.get()
            ),
        )
    }

    @SuppressLint("TimberArgCount")
    override fun v(message: String, vararg args: Any?) {
        Timber.v("$message -> ${Thread.currentThread()}", *args)
    }

    @SuppressLint("TimberArgCount")
    override fun v(t: Throwable, message: String, vararg args: Any?) {
        Timber.v(t, "$message -> ${Thread.currentThread()}", *args)
    }

    override fun v(t: Throwable) {
        Timber.v(t)
    }

    override fun d(message: String, vararg args: Any?) {
        Timber.d(message, *args)
    }

    override fun d(t: Throwable, message: String, vararg args: Any?) {
        Timber.d(t, message, *args)
    }

    override fun d(t: Throwable) {
        Timber.d(t)
    }

    override fun i(message: String, vararg args: Any?) {
        Timber.i(message, *args)
    }

    override fun i(t: Throwable, message: String, vararg args: Any?) {
        Timber.i(t, message, *args)
    }

    override fun i(t: Throwable) {
        Timber.i(t)
    }

    override fun w(message: String, vararg args: Any?) {
        Timber.w(message, *args)
    }

    override fun w(t: Throwable, message: String, vararg args: Any?) {
        Timber.w(t, message, *args)
    }

    override fun w(t: Throwable) {
        Timber.w(t)
    }

    override fun e(message: String, vararg args: Any?) {
        Timber.e(message, *args)
    }

    override fun e(t: Throwable, message: String, vararg args: Any?) {
        Timber.e(t, message, *args)
    }

    override fun e(t: Throwable) {
        Timber.e(t)
    }

    override fun wtf(message: String, vararg args: Any?) {
        Timber.wtf(message, *args)
    }

    override fun wtf(t: Throwable, message: String, vararg args: Any?) {
        Timber.wtf(t, message, *args)
    }

    override fun wtf(t: Throwable) {
        Timber.wtf(t)
    }
}

val defaultLogger: Logger by lazy { AppLogger() }

/**
 * Special version of [Timber.DebugTree] which is tailored for Timber being wrapped
 * within another class.
 */
private class AppDebugTree : Timber.DebugTree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        super.log(priority, createClassTag(), message, t)
    }

    private fun createClassTag(): String {
        val stackTrace = Throwable().stackTrace
        if (stackTrace.size <= CALL_STACK_INDEX) {
            throw IllegalStateException("Synthetic stacktrace didn't have enough elements: are you using proguard?")
        }
        var tag = stackTrace[CALL_STACK_INDEX].className

        if (tag.contains("Logger\$DefaultImpls")) {
            tag = stackTrace[CALL_STACK_INDEX + 2].className
        }

        val m = ANONYMOUS_CLASS.matcher(tag)
        if (m.find()) {
            tag = m.replaceAll("")
        }
        tag = tag.substring(tag.lastIndexOf('.') + 1)
        // Tag length limit was removed in API 24.
        return tag
    }

    companion object {
        private const val CALL_STACK_INDEX = 6
        private val ANONYMOUS_CLASS by lazy { Pattern.compile("(\\$.+)+$") }
    }
}

private class CrashlyticsTree : Timber.Tree() {
    override fun isLoggable(tag: String?, priority: Int): Boolean {
        return priority >= Log.INFO
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
//        firebaseCrashlytics.log(message)
//        if (t != null) {
//            firebaseCrashlytics.recordException(t)
//        }
    }
}
