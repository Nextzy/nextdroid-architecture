package com.nextzy.library.template.mvvm.adapter.item


import android.annotation.SuppressLint
import com.nextzy.library.base.view.holder.base.BaseItem
import com.nextzy.library.template.mvvm.adapter.operator.KotlinMvvmCreator
import kotlinx.android.parcel.Parcelize

/**
 * Created by「 The Khaeng 」on 03 Oct 2017 :)
 */

@SuppressLint("ParcelCreator")
@Parcelize
open class KotlinMvvmCustomItem() : BaseItem(type = KotlinMvvmCreator.TYPE_CUSTOM_ITEM) {

}
