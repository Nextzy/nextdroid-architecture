package com.nextzy.library.customize.adapter

import android.content.Context
import android.util.AttributeSet

import com.nextzy.library.base.utils.DefaultLinearLayoutManager


/**
 * Created by「 The Khaeng 」on 23 Aug 2017 :)
 */

class CustomLayoutManager : DefaultLinearLayoutManager {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, orientation: Int, reverseLayout: Boolean) : super(context, orientation, reverseLayout) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {}
}
