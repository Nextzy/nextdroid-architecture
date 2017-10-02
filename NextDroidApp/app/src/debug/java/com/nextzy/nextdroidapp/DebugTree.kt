package th.co.thekhaeng.waterbottleminder

import android.util.Log

import com.facebook.stetho.inspector.console.CLog
import com.facebook.stetho.inspector.console.ConsolePeerManager
import com.facebook.stetho.inspector.protocol.module.Console

import timber.log.Timber

/**
 * Created by「 The Khaeng 」on 28 Aug 2017 :)
 */

class DebugTree : Timber.DebugTree() {

    override
    fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        super.log(priority, tag, message, t)

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
                message
                           )
    }

    override
    fun createStackElementTag(element: StackTraceElement): String {
        return super.createStackElementTag(element) + ":" + element.lineNumber
    }
}
