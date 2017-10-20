package com.nextzy.library.base.utils

import android.content.Context
import android.support.v7.widget.LinearSmoothScroller
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.AttributeSet

/**
 * Created by「 The Khaeng 」on 23 Aug 2017 :)
 */

open class DefaultStaggeredGridLayoutManager : StaggeredGridLayoutManager {

    companion object {
        private val MILLISECONDS_PER_INCH = 100f //default is 25f (bigger = slower)
    }


    private var smoothScroller: LinearSmoothScroller? = null
    private var context: Context? = null


    constructor(spanCount:Int, orientation:Int) : super(spanCount, orientation) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context)
    }




    private fun init(context: Context?) {
        this.context = context
    }
}