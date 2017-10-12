package com.nextzy.tabcustomize.base.mvvm

import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.nextzy.library.R
import com.nextzy.tabcustomize.base.mvvm.dialog.DialogHelperMvvmActivity


/**
* Created by「 The Khaeng 」on 03 Oct 2017 :)
*/

abstract class CustomMvvmActivity
    : DialogHelperMvvmActivity() {


    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setTaskDescription(
                    getString(R.string.app_name),
                    null,
                    ContextCompat.getColor(this, R.color.md_white))
        }
    }

}

