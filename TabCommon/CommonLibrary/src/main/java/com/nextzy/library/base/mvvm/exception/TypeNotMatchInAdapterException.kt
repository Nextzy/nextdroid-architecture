package com.nextzy.library.base.mvvm.exception

/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

class TypeNotMatchInAdapterException(className: String, type: Int)
    : RuntimeException("Type in $className not match. Value: $type")

