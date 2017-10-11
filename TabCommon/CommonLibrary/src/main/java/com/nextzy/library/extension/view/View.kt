package com.nextzy.library.extension.view

import android.content.res.Resources
import android.graphics.Rect
import android.support.annotation.Px
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import com.thekhaeng.pushdownanim.PushDownAnim

/**
 * Created by「 The Khaeng 」on 03 Oct 2017 :)
 */

interface OnGetViewSizeListener {
    fun onSize(width: Int, height: Int)
}

fun View?.setOnTouchAnimScale(scale: Float = 0.96f,
                              listener: View.OnClickListener? = null) {
    PushDownAnim
            .setOnTouchPushDownAnim(this)
            .setOnClickListener(listener)
            .setScale(scale)
}

fun View?.getSize(listener: OnGetViewSizeListener?) {
    this?.viewTreeObserver?.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
        override
        fun onPreDraw(): Boolean {
            this@getSize.viewTreeObserver.removeOnPreDrawListener(this)
            listener?.onSize(this@getSize.width, this@getSize.height)
            return false
        }
    })
}

fun spToPx(px: Int): Float {
    return spToPx(px.toFloat())
}

fun spToPx(sp: Float): Float {
    return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            sp,
            Resources.getSystem().displayMetrics)

}

fun dpToPx(px: Int): Float {
    return dpToPx(px.toFloat())
}

fun dpToPx(dp: Float): Float {
    return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            Resources.getSystem().displayMetrics)
}

fun pxToDp(px: Int): Float {
    return pxToDp(px.toFloat())
}

fun pxToDp(px: Float): Float {
    return px / Resources.getSystem().displayMetrics.density
}

fun pxToSp(px: Int): Float {
    return pxToSp(px.toFloat())
}

fun pxToSp(px: Float): Float {
    return px / Resources.getSystem().displayMetrics.scaledDensity
}


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




