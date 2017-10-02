package com.nextzy.library.extension

import android.content.res.Resources

/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

fun pxToDp(px: Float): Float {
    val metrics = Resources.getSystem().displayMetrics
    val dp = px / (metrics.densityDpi / 160f)
    return Math.round(dp).toFloat()
}

fun dpToPx(dp: Float): Float {
    val metrics = Resources.getSystem().displayMetrics
    val px = dp * (metrics.densityDpi / 160f)
    return Math.round(px).toFloat()
}

fun pxToSp(px: Float): Float {
    val metrics = Resources.getSystem().displayMetrics
    val dp = px / (metrics.scaledDensity / 160f)
    return Math.round(dp).toFloat()
}

fun spToPx(dp: Float): Float {
    val metrics = Resources.getSystem().displayMetrics
    val px = dp * (metrics.scaledDensity / 160f)
    return Math.round(px).toFloat()
}


