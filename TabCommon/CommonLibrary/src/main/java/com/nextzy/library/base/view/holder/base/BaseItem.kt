package com.nextzy.library.base.view.holder.base

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by「 The Khaeng 」on 26 Sep 2017 :)
 */

abstract class BaseItem(
        val id: Int = 0,
        var type: Int) : Parcelable {

    constructor(source: Parcel) : this(
            source.readInt(), source.readInt() )

    override
    fun describeContents() = 0

    override
    fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeInt(type)
    }
}
