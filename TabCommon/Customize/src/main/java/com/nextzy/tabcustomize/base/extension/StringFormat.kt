package com.nextzy.tabcustomize.base.extension

/**
 * Created by「 The Khaeng 」on 03 Oct 2017 :)
 */
val DEFAULT_SEPARATE = "-"
val PATTERN_MOBILE_NUMBER = "###-###-####"
val PATTERN_ID_CARD = "#-####-#####-##-#"

fun String?.applyStringPattern(format: String,
                               separate: String = DEFAULT_SEPARATE): String? {
    // ex. pattern "(\\d{3})(\\d{3})(\\d+)"
    var pattern = ""
    // ex. replacement "$1-$2-$3"
    var replacement = ""
    val formats = format.split(separate.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    for (i in formats.indices) {
        pattern += "(\\d{" + formats[i].length + "})"
        if (i == 0) {
            replacement += "$" + (i + 1)
        } else {
            replacement += separate + "$" + (i + 1)
        }
    }
    return this?.replaceFirst(pattern.toRegex(), replacement)
}


fun String?.noSeparate(text: String,
                       separate: String = DEFAULT_SEPARATE): String {
    return if (this?.contains(separate) == true) {
        this.replace(separate, "")
    } else text
}