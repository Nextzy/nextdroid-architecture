package com.nextzy.library.extension

import android.app.Activity
import android.graphics.Point
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

const val DEFAULT_DELAY = 1000

val Activity?.activityScreenSize: Point
    get() {
        val point = Point()
        this?.windowManager?.defaultDisplay?.getSize(point)
        return point
    }


inline fun Activity?.delay(action: Action, delay: Long = DEFAULT_DELAY.toLong()) {
    Observable.empty<Any>()
            .delay(delay, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete(action)
            .subscribe()
}


