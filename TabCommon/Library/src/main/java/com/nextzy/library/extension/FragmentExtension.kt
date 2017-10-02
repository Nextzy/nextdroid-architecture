package com.nextzy.library.extension

import android.support.v4.app.Fragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

fun Fragment?.delay(action: Action, delay: Long = DEFAULT_DELAY.toLong()) {
    Observable.empty<Any>()
            .delay(delay, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete(action)
            .subscribe()
}

