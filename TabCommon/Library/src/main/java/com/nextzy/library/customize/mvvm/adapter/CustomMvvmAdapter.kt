package com.nextzy.library.customize.mvvm.adapter


import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.nextzy.library.base.mvvm.layer1View.BaseMvvmListAdapter
import com.nextzy.library.base.view.holder.base.BaseViewHolder
import com.nextzy.library.customize.mvvm.CustomViewModel


/**
 * Created by「 The Khaeng 」on 19 Sep 2017 :)
 */

abstract class CustomMvvmAdapter<
        VH : BaseViewHolder<*>,
        VM : CustomViewModel>
    : BaseMvvmListAdapter<VH, VM> {

    constructor(activity: FragmentActivity) : super(activity) {}

    constructor(fragment: Fragment) : super(fragment) {}


}

