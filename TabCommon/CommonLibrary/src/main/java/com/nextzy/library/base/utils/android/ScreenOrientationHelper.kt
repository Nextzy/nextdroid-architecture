package com.nextzy.library.base.utils.android

import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.FragmentActivity


/**
 * Created by「 The Khaeng 」on 18 Sep 2017 :)
 */

class ScreenOrientationHelper {
    private var activity: Activity? = null
    private var listener: ScreenOrientationChangeListener? = null
    private var lastOrientation: Int? = 0


    companion object {
        val KEY_LAST_ORIENTATION = "last_orientation"
    }

    interface ScreenOrientationChangeListener {
        fun onScreenOrientationChanged(orientation: Int)

        fun onScreenOrientationChangedToLandscape()

        fun onScreenOrientationChangedToPortrait()
    }

    fun setActivity(activity: FragmentActivity) {
        this.activity = activity
    }

    fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            getActivity()?.let {
                lastOrientation = it.resources.configuration.orientation
            }
        }
    }

    fun checkOrientation() {
        checkOrientationChanged()
    }

    fun onRestoreInstanceState(savedInstanceState: Bundle) {
        restoreLastOrientationState(savedInstanceState)
    }

    fun onSaveInstanceState(outState: Bundle?) {
        saveLastOrientationState(outState)
    }

    private fun restoreLastOrientationState(savedInstanceState: Bundle?) {
        lastOrientation = savedInstanceState?.getInt(KEY_LAST_ORIENTATION)
                ?: getActivity()?.let { it.resources.configuration.orientation }
    }

    private fun saveLastOrientationState(outState: Bundle?) {
        outState?.putInt(KEY_LAST_ORIENTATION, lastOrientation ?: -1)
    }

    private fun checkOrientationChanged() {
        getActivity()?.let {
            val currentOrientation = it.resources.configuration.orientation
            if (currentOrientation != lastOrientation) {
                listener?.onScreenOrientationChanged(currentOrientation)
                if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                    listener?.onScreenOrientationChangedToLandscape()
                } else if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
                    listener?.onScreenOrientationChangedToPortrait()
                }
                lastOrientation = currentOrientation
            }
        }
    }

    fun setScreenOrientationChangeListener(listener: ScreenOrientationChangeListener) {
        this.listener = listener
    }

    private fun getActivity(): Activity? {
        if (activity == null) throw IllegalArgumentException("You haven't setActivity(...)")
        return activity
    }
}
