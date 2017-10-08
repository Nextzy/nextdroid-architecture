package com.nextzy.tabcustomize.template.mvvm

import android.os.Bundle

import com.nextzy.tabcustomize.base.mvvm.CustomMvvmActivity


/**
* Created by「 The Khaeng 」on 08 Oct 2017 :)
*/

class KotlinMvvmCustomActivity : CustomMvvmActivity<KotlinMvvmCustomViewModel>() {

    override
    fun setupViewModel(): Class<KotlinMvvmCustomViewModel>?
            = KotlinMvvmCustomViewModel::class.java

    override
    fun setupLayoutView(): Int = 0

    override
    fun bindView() {

    }

    override
    fun setupInstance() {

    }

    override
    fun setupView() {

    }

    override
    fun initialize() {

    }

    override
    fun onRestoreArgument(bundle: Bundle) {
        super.onRestoreArgument(bundle)
    }

    override
    fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
    }

    override
    fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }

}

