package com.nextzy.library.base.view.holder.base

import android.os.Parcelable

/**
 * Created by「 The Khaeng 」on 26 Sep 2017 :)
 */

abstract class BaseItem(
        val id: Int = 0,
        var type: Int ) : Parcelable {
}
