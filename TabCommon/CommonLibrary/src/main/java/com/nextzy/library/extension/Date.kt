package com.nextzy.library.extension

import android.support.annotation.IntDef
import java.text.DateFormat
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by「 The Khaeng 」on 03 Oct 2017 :)
 */
const val MSEC = 1
const val SEC = 1000
const val MIN = 60000
const val HOUR = 3600000
const val DAY = 86400000

@IntDef(MSEC.toLong(), SEC.toLong(), MIN.toLong(), HOUR.toLong(), DAY.toLong())
@Retention(AnnotationRetention.SOURCE)
annotation class TimeMillis


fun dateNow(): String = Date().asString()

fun timestamp(): Long = System.currentTimeMillis()

fun dateParse(s: String): Date = DateHelper.DATE_FORMAT_SIMPLE_FORMAT.get().parse(s, ParsePosition(0))

fun Date.asString(format: DateFormat): String = format.format(this)

fun Date.asString(format: String): String = asString(SimpleDateFormat(format, Locale.US))

fun Date.asString(): String = DateHelper.DATE_FORMAT_SIMPLE_FORMAT.get().format(this)

fun Long.asDateString(): String = Date(this).asString()

object DateHelper {
    const val DATE_FORMAT_SIMPLE_STRING = "yyyy-MM-dd HH:mm:ss"
    @JvmField
    val DATE_FORMAT_SIMPLE_FORMAT = object : ThreadLocal<DateFormat>() {
        override
        fun initialValue(): DateFormat {
            return SimpleDateFormat(DATE_FORMAT_SIMPLE_STRING, Locale.US)
        }
    }
}