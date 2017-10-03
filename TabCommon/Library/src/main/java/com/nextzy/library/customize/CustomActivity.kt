package com.nextzy.library.customize

import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.nextzy.library.R
import com.nextzy.library.base.mvvm.layer1View.DialogHelperActivity


/**
* Created by「 The Khaeng 」on 03 Oct 2017 :)
*/

abstract class CustomActivity : DialogHelperActivity() {

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

