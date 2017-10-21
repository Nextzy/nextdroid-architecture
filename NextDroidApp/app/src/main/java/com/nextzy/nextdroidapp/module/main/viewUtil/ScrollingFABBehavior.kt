package com.nextzy.nextdroidapp.module.main.viewUtil

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View


/**
 * Created by「 The Khaeng 」on 21 Oct 2017 :)
 */

class ScrollingFABBehavior(context: Context, attrs: AttributeSet)
    : CoordinatorLayout.Behavior<FloatingActionButton>(context, attrs) {
    private val toolbarHeight: Int

    init {
        toolbarHeight = getToolbarHeight(context)
    }

    override
    fun layoutDependsOn(parent: CoordinatorLayout?, fab: FloatingActionButton?, dependency: View?): Boolean {
        return dependency is AppBarLayout
    }

    override
    fun onDependentViewChanged(parent: CoordinatorLayout?, fab: FloatingActionButton?, dependency: View?): Boolean {
        if (dependency is AppBarLayout) {
            fab?.let {
                val lp = fab.layoutParams as CoordinatorLayout.LayoutParams
                val fabBottomMargin = lp.bottomMargin
                val distanceToScroll = fab.height + fabBottomMargin
                val ratio = dependency.y / toolbarHeight.toFloat()
                fab.translationY = (-distanceToScroll * ratio) + (distanceToScroll / 2)
            }
        }
        return true
    }

    private fun getToolbarHeight(context: Context): Int {
        val tv = TypedValue()
        return if (context.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            TypedValue.complexToDimensionPixelSize(tv.data, context.resources.displayMetrics)
        } else {
            0
        }
    }
}
