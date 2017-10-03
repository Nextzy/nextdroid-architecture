package com.nextzy.library.extension

import android.graphics.Rect
import android.support.annotation.Px
import android.view.View
import android.view.ViewGroup

/**
 * Created by「 The Khaeng 」on 03 Oct 2017 :)
 */
 fun View?.isViewIntersect(otherView: View?): Boolean {
    if (this == null || otherView == null) return false

    val view1Loc = IntArray(2)
    this.getLocationOnScreen(view1Loc)
    val view1Rect = Rect(view1Loc[0],
                         view1Loc[1],
                         view1Loc[0] + this.width,
                         view1Loc[1] + this.height)
    val view2Loc = IntArray(2)
    otherView.getLocationOnScreen(view2Loc)
    val view2Rect = Rect(view2Loc[0],
                         view2Loc[1],
                         view2Loc[0] + otherView.width,
                         view2Loc[1] + otherView.height)
    return view1Rect.intersect(view2Rect)
}

fun View?.show(isShow: Boolean?) {
    this?.visibility = if (isShow == true) View.VISIBLE else View.GONE
}

fun View?.show() {
    this?.visibility = View.VISIBLE
}

fun View?.hide() {
    this?.visibility = View.GONE
}

fun View?.setWidthViewSize(@Px width: Int) {
    val params = this?.layoutParams
    params?.width = width
    this?.layoutParams = params
    this?.requestLayout()
}

fun View?.setHeightViewSize(@Px height: Int) {
    val params = this?.layoutParams
    params?.height = height
    this?.layoutParams = params
    this?.requestLayout()
}

fun View?.setViewSize(@Px width: Int, @Px height: Int) {
    val params = this?.layoutParams
    params?.height = height
    params?.width = width
    this?.layoutParams = params
    this?.requestLayout()
}


fun View?.setPaddingStart(@Px paddingStart: Int) {
    this?.setPaddingRelative(paddingStart,
                             this.paddingTop,
                             this.paddingEnd,
                             this.paddingBottom)
}

fun View?.setPaddingTop(@Px paddingTop: Int) {
    this?.setPaddingRelative(this.paddingStart,
                             paddingTop,
                             this.paddingEnd,
                             this.paddingBottom)
}

fun View?.setPaddingEnd(@Px paddingEnd: Int) {
    this?.setPaddingRelative(this.paddingStart,
                             this.paddingTop,
                             paddingEnd,
                             this.paddingBottom)
}

fun View?.setPaddingBottom(@Px paddingBottom: Int) {
    this?.setPaddingRelative(this.paddingStart,
                             this.paddingTop,
                             this.paddingEnd,
                             paddingBottom)
}

fun View?.setMargin(@Px left: Int = -1,
                    @Px top: Int = -1,
                    @Px right: Int = -1,
                    @Px bottom: Int = -1) {
    val marginParams = this?.layoutParams as ViewGroup.MarginLayoutParams
    marginParams.setMargins(
            if (left == -1) marginParams.leftMargin else left,
            if (top == -1) marginParams.topMargin else top,
            if (right == -1) marginParams.rightMargin else right,
            if (bottom == -1) marginParams.bottomMargin else bottom)
    this.requestLayout()
}


fun View?.addMargin(@Px left: Int = -1,
                    @Px top: Int = -1,
                    @Px right: Int = -1,
                    @Px bottom: Int = -1) {
    val marginParams = this?.layoutParams as ViewGroup.MarginLayoutParams
    marginParams.setMargins(
            if (left == -1) marginParams.leftMargin else marginParams.leftMargin + left,
            if (top == -1) marginParams.topMargin else marginParams.topMargin + top,
            if (right == -1) marginParams.rightMargin else marginParams.rightMargin + right,
            if (bottom == -1) marginParams.bottomMargin else marginParams.bottomMargin + bottom)
    this.requestLayout()
}




