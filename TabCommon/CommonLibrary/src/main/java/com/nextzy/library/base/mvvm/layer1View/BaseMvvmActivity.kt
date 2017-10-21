package com.nextzy.library.base.mvvm.layer1View

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import com.nextzy.library.base.delegate.RxDelegation
import io.reactivex.disposables.Disposable

/**
 * Created by「 The Khaeng 」on 27 Aug 2017 :)
 */

abstract class BaseMvvmActivity : SettingHelperActivity() {

    private val rxDelegation = RxDelegation()

    override
    fun setupInstance() {
        setupViewModel()
        super.setupInstance()
    }

    open fun setupViewModel() {

    }

    override
    fun onDestroy() {
        super.onDestroy()
        rxDelegation.clearAllDisposables()
    }

    fun <VM : ViewModel> getViewModel(viewModelClass: Class<VM>): VM
            = ViewModelProviders
            .of(this)
            .get(viewModelClass)


    fun addDisposable(d: Disposable) {
        rxDelegation.addDisposable(d)
    }

}

