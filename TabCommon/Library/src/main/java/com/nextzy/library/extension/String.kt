package com.nextzy.library.extension

import java.util.regex.Pattern

/**
 * Created by「 The Khaeng 」on 03 Oct 2017 :)
 */

fun String.isInt(): Boolean {
    return Pattern.matches("\\d+", this)
}

fun String.isFloat(): Boolean {
    return Pattern.matches("^([+-]?\\d+\\.?\\d+)\$", this)
}

fun String.removeNonNumeric(): String? {
    return this?.replace(",".toRegex(), "")?.replace("[^\\d|\\.]*$".toRegex(), "")
}