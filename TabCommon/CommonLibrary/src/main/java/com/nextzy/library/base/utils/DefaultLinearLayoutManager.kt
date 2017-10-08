package com.nextzy.library.base.utils

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSmoothScroller
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.DisplayMetrics

/**
 * Created by「 The Khaeng 」on 23 Aug 2017 :)
 */

open class DefaultLinearLayoutManager : LinearLayoutManager {

    companion object {
        private val MILLISECONDS_PER_INCH = 100f //default is 25f (bigger = slower)
    }


    private var smoothScroller: LinearSmoothScroller? = null
    private var context: Context? = null

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, orientation: Int, reverseLayout: Boolean) : super(context, orientation, reverseLayout) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context)
    }


    /**
     * Set for preload in RecyclerView.
     *
     * @param state
     */
    override
    fun getExtraLayoutSpace(state: RecyclerView.State): Int {
        return 300
    }


    private fun init(context: Context) {
        this.context = context
        smoothScroller = object : LinearSmoothScroller(context) {
            public override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                return MILLISECONDS_PER_INCH / displayMetrics.densityDpi
            }

            override fun calculateDtToFit(viewStart: Int, viewEnd: Int, boxStart: Int, boxEnd: Int, snapPreference: Int): Int {
                return boxStart + (boxEnd - boxStart) / 2 - (viewStart + (viewEnd - viewStart) / 2)
            }
        }
    }

    override
    fun smoothScrollToPosition(recyclerView: RecyclerView,
                                        state: RecyclerView.State?, position: Int) {
        smoothScroller?.targetPosition = position
        startSmoothScroll(smoothScroller)
    }

    fun scrollWindowToCenterItem(pos: Int) {
        smoothScroller?.targetPosition = pos
        startSmoothScroll(smoothScroller)
    }

}
