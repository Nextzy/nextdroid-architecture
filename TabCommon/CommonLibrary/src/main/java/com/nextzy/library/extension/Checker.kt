@file:Suppress("UNCHECKED_CAST")

package com.nextzy.library.extension

/**
 * Created by「 The Khaeng 」on 04 Jan 2018 :)
 */

@Suppress("NAME_SHADOWING")
inline fun <A, B, R> Pair<A?, B?>.notnull(block: (first: A, second: B) -> R): R? =
        if (first != null && second != null) block(first!!, second!!) else null

@Suppress("NAME_SHADOWING")
inline fun <A, B, C, R> Triple<A?, B?, C?>.notnull(block: (first: A, second: B, third: C) -> R): R? =
        if (first != null && second != null && third != null) block(first!!, second!!, third!!) else null


