package com.nextzy.library.base.mvvm.layer1View

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.content.Context

/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

abstract class BaseMvvmDialogFragment
    : BaseDialogFragment(){


    fun <VM : ViewModel> getViewModel(viewModelClass: Class<VM>): VM
            = ViewModelProviders.of(this).get(viewModelClass)

    fun <VM : ViewModel> getViewModel(key: String, viewModelClass: Class<VM>): VM
            = ViewModelProviders.of(this).get(key, viewModelClass)

    fun <VM : ViewModel> getSharedViewModel(viewModelClass: Class<VM>): VM
            = ViewModelProviders.of(activity).get(viewModelClass)

    override
    fun onAttach(context: Context?) {
        super.onAttach(context)
        onSetupViewModel()
    }

    open fun onSetupViewModel() {

    }

}