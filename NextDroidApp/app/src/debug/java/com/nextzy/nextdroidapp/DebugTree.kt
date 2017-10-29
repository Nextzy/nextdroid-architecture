package com.nextzy.nextdroidapp

import android.util.Log
import com.facebook.stetho.inspector.console.CLog
import com.facebook.stetho.inspector.console.ConsolePeerManager
import com.facebook.stetho.inspector.protocol.module.Console
import timber.log.Timber
import java.util.regex.Pattern




/**
 * Created by「 The Khaeng 」on 28 Aug 2017 :)
 */

class DebugTree : Timber.DebugTree() {

    private val CALL_STACK_INDEX = 5
    private val ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$")


    override
    fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        val stackTrace = Throwable().stackTrace
        if (stackTrace.size <= CALL_STACK_INDEX) {
            throw IllegalStateException(
                    "Synthetic stacktrace didn't have enough elements: are you using proguard?")
        }
        val clazz = extractClassName(stackTrace[CALL_STACK_INDEX])
        val lineNumber = stackTrace[CALL_STACK_INDEX].lineNumber
        val newMessage = ".($clazz:$lineNumber) - $message"

        super.log(priority, tag, newMessage, t)

        // Stetho
        val peerManager = ConsolePeerManager.getInstanceOrNull() ?: return

        val logLevel: Console.MessageLevel = when (priority) {
            Log.VERBOSE, Log.DEBUG -> Console.MessageLevel.DEBUG
            Log.INFO -> Console.MessageLevel.LOG
            Log.WARN -> Console.MessageLevel.WARNING
            Log.ERROR, Log.ASSERT -> Console.MessageLevel.ERROR
            else -> Console.MessageLevel.LOG
        }

        CLog.writeToConsole(
                logLevel,
                Console.MessageSource.OTHER,
                message )
    }

    override
    fun createStackElementTag(element: StackTraceElement): String {
        return super.createStackElementTag(element) + ":" + element.lineNumber
    }

    /**
     * Extract the class name without any anonymous class suffixes (e.g., `Foo$1`
     * becomes `Foo`).
     */
    private fun extractClassName(element: StackTraceElement): String {
        var tag = element.fileName
        val m = ANONYMOUS_CLASS.matcher(tag)
        if (m.find()) {
            tag = m.replaceAll("")
        }
        return tag
    }
}
