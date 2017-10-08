package com.nextzy.library.base.view

import android.content.Context
import android.util.AttributeSet
import android.view.View


/**
 * Created by「 The Khaeng 」on 03 Oct 2017 :)
 */
abstract class BaseView
@JvmOverloads constructor(context: Context,
                          attrs: AttributeSet? = null,
                          defStyleAttr: Int = 0,
                          defStyleRes: Int = 0)
    : View(context, attrs, defStyleAttr, defStyleRes) {

    init {
        init()
    }

    private fun init() {}

}
