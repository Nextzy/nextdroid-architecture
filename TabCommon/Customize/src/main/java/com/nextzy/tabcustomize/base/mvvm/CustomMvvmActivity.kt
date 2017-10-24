package com.nextzy.tabcustomize.base.mvvm

import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.nextzy.library.R
import com.nextzy.tabcustomize.base.mvvm.animation.AnimationHelperMvvmActivity


/**
* Created by「 The Khaeng 」on 03 Oct 2017 :)
*/

abstract class CustomMvvmActivity
    : AnimationHelperMvvmActivity() {


    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}

