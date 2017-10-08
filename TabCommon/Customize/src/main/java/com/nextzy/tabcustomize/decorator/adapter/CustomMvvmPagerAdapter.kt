package com.nextzy.tabcustomize.decorator.adapter


import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.nextzy.library.base.mvvm.layer1View.BaseMvvmPagerAdapter
import com.nextzy.tabcustomize.base.mvvm.CustomViewModel


/**
* Created by「 The Khaeng 」on 03 Oct 2017 :)
*/

abstract class CustomMvvmPagerAdapter<VM : CustomViewModel>
    : BaseMvvmPagerAdapter<VM> {

    constructor(activity: FragmentActivity) : super(activity) {}

    constructor(fragment: Fragment) : super(fragment) {}
}

