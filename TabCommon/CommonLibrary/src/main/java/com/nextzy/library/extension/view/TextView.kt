package com.nextzy.library.extension.view

import android.widget.TextView
import java.text.NumberFormat
import java.util.*

/**
 * Created by「 The Khaeng 」on 03 Oct 2017 :)
 */

fun TextView.setText(number: Int) {
    this.text = NumberFormat.getIntegerInstance(Locale.US).format(number)
}