package com.nextzy.library.extension

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Created by「 The Khaeng 」on 03 Oct 2017 :)
 */

fun Activity.showSoftInput() {
    var view = this.currentFocus
    if (view == null) view = View(this)
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager ?: return
    imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
}


fun Activity.hideSoftInput() {
    var view = this.currentFocus
    if (view == null) view = View(this)
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager ?: return
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun View.showSoftInput() {
    this.isFocusable = true
    this.isFocusableInTouchMode = true
    this.requestFocus()
    val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager ?: return
    imm.showSoftInput(this, InputMethodManager.SHOW_FORCED)
}


fun View.hideSoftInput() {
    val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager ?: return
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

fun Context.toggleSoftInput() {
    val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager ?: return
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

