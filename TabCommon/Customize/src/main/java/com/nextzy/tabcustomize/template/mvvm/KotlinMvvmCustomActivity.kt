package com.nextzy.tabcustomize.template.mvvm

import android.os.Bundle

import com.nextzy.tabcustomize.base.mvvm.CustomMvvmActivity


/**
* Created by「 The Khaeng 」on 08 Oct 2017 :)
*/

class KotlinMvvmCustomActivity : CustomMvvmActivity() {

    private lateinit var viewModel: KotlinMvvmCustomViewModel

    override
    fun onSetupViewModel() {
        viewModel = getViewModel(KotlinMvvmCustomViewModel::class.java)
    }

    override
    fun setupLayoutView(): Int = 0


    override
    fun onInitialize() {
        super.onInitialize()
    }

    override
    fun onSetupView() {
        super.onSetupView()
    }

    override
    fun onPrepareInstance() {
        super.onPrepareInstance()
    }

    override
    fun onRestoreView(savedInstanceState: Bundle) {
        super.onRestoreView(savedInstanceState)
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
    fun onBeforeRestoreInstanceStateView(savedInstanceState: Bundle) {
        super.onBeforeRestoreInstanceStateView(savedInstanceState)
    }

}

