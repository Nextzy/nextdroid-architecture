package com.nextzy.library.extension.view

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Point
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

const val DEFAULT_DELAY = 1000

fun Activity.screenShot(isDeleteStatusBar: Boolean = false): Bitmap {
    val decorView = this.window.decorView
    decorView.isDrawingCacheEnabled = true
    decorView.buildDrawingCache()
    val bmp = decorView.drawingCache
    val dm = DisplayMetrics()
    this.windowManager.defaultDisplay.getMetrics(dm)
    val ret: Bitmap = if (isDeleteStatusBar) {
        val resources = this.resources
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        val statusBarHeight = resources.getDimensionPixelSize(resourceId)
        Bitmap.createBitmap(bmp, 0, statusBarHeight, dm.widthPixels, dm.heightPixels - statusBarHeight)
    } else {
        Bitmap.createBitmap(bmp, 0, 0, dm.widthPixels, dm.heightPixels)
    }
    decorView.destroyDrawingCache()
    return ret
}


fun Activity?.toggleKeyboard() {
    this?.apply {
        val view: View? = findViewById(android.R.id.content)
        if (view != null) {
            val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }
    }
}


fun Activity?.hideKeyboard(view: View? = this?.findViewById(android.R.id.content)) {
    this?.apply {
        if (view != null) {
            val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN)
        }
    }
}


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


