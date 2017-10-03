package com.nextzy.library.extension

import android.support.annotation.IntDef

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