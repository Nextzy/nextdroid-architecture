package com.nextzy.nextdroidapp

import android.annotation.SuppressLint
import android.util.Log

import timber.log.Timber

/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

class ReleaseTree : Timber.Tree() {

    companion object {
        val MAX_LOG_LENGTH = 4000
    }

    override
    fun isLoggable(tag: String, priority: Int): Boolean {
        // Only log WARN, ERROR, WTF
        return !(priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO)
    }

    @SuppressLint("LogTagMismatch")
    override
    fun log(priority: Int, tag: String, message: String, t: Throwable?) {
        if (isLoggable(tag, priority)) {
            //Report Crash
            if (priority == Log.ERROR || t != null) {
                // Crashlytic.log(e);
            }

            // Message is short enough, does not need
            if (message.length < MAX_LOG_LENGTH) {
                when (priority) {
                    Log.ASSERT -> Log.wtf(tag, message)
                    Log.ERROR -> Log.e(tag, message)
                    else -> Log.println(priority, tag, message)
                }
                return
            }

            // Split line by '\n'
            var i = 0
            val length = message.length
            while (i < length) {
                var newline = message.indexOf('\n', i)
                newline = if (newline != -1) newline else length
                do {
                    val end = Math.min(length, i + MAX_LOG_LENGTH)
                    val part = message.substring(i, end)
                    when (priority) {
                        Log.ASSERT -> Log.wtf(tag, part)
                        Log.ERROR -> Log.e(tag, part)
                        else -> Log.println(priority, tag, part)
                    }
                    i = end
                } while (i < newline)
                i++
            }
        }
    }


}
