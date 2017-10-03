package com.nextzy.library.customize.mvvm.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.nextzy.library.base.mvvm.layer1View.BaseMvvmIndividualListAdapter
import com.nextzy.library.base.mvvm.layer2ViewModel.BaseListAdapterViewModel
import com.nextzy.library.base.view.holder.base.BaseViewHolder

/**
 * Created by「 The Khaeng 」on 18 Sep 2017 :)
 */
abstract class CustomMvvmIndividualListAdapter<
        VH : BaseViewHolder<*>,
        VM : BaseListAdapterViewModel>
    : BaseMvvmIndividualListAdapter<VH, VM> {

    constructor(activity: FragmentActivity) : super(activity) {}

    constructor(fragment: Fragment) : super(fragment) {}


}