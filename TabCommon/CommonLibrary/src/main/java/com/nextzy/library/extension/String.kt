package com.nextzy.library.extension

import java.util.regex.Pattern

/**
 * Created by「 The Khaeng 」on 03 Oct 2017 :)
 */

fun String.quote(): String {
    return "'$this'"
}

fun CharSequence.isBlank(): Boolean {
    val len: Int = this.length
    if (len == 0) {
        return true
    }
    forEach { c ->
        if (!Character.isWhitespace(c)) {
            return false
        }
    }
    return true
}

fun String.trimAllWhitespace(): String {
    if (this.isEmpty()) {
        return this
    }
    val sb = StringBuilder(this)
    var index = 0
    while (sb.length > index) {
        if (Character.isWhitespace(sb[index])) {
            sb.deleteCharAt(index)
        } else {
            index++
        }
    }
    return sb.toString()
}

fun CharSequence.containsWhitespace(): Boolean {
    if (this.isEmpty()) {
        return false
    }
    forEach { c ->
        if (Character.isWhitespace(c)) {
            return true
        }
    }
    return false
}

fun String.isInt(): Boolean {
    return Pattern.matches("\\d+", this)
}

fun String.isFloat(): Boolean {
    return Pattern.matches("^([+-]?\\d+\\.?\\d+)\$", this)
}

fun String.removeNonNumeric(): String? {
    return this.replace(",".toRegex(), "").replace("[^\\d|\\.]*$".toRegex(), "")
}