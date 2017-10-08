package com.nextzy.library.extension.view

import android.support.annotation.DimenRes
import android.support.v4.app.Fragment
import com.nextzy.library.extension.getFloatDimen
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

fun Fragment.getFloatDimen(@DimenRes resId: Int): Float? {
    return this.context.getFloatDimen(resId)
}

fun Fragment.delay(action: Action, delay: Long = DEFAULT_DELAY.toLong()) {
    Observable.empty<Any>()
            .delay(delay, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete(action)
            .subscribe()
}

