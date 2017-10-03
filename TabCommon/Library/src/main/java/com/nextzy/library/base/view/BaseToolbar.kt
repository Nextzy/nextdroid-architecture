package com.nextzy.library.base.view

import android.content.Context
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.view.View

/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

abstract class BaseToolbar : Toolbar, View.OnClickListener {

    constructor(context: Context) : super(context) {
        setup(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setup(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setup(context)
    }

    fun setup(context: Context) {
        View.inflate(context, setupLayoutView(), this)
        bindView()
        setupView()
        setInset()
    }


    private fun setInset() {
        setContentInsetsAbsolute(0, 0)
        setContentInsetsRelative(0, 0)
        setPadding(0, 0, 0, 0) //require: for tablet
    }

    protected abstract fun setupLayoutView(): Int

    protected abstract fun bindView()

    protected abstract fun setupView()

}
