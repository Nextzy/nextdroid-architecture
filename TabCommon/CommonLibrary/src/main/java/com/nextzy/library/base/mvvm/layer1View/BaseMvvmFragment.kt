package com.nextzy.library.base.mvvm.layer1View

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.nextzy.library.base.delegate.RxDelegation
import io.reactivex.disposables.Disposable


/**
 * Created by「 The Khaeng 」on 24 Aug 2017 :)
 */

abstract class BaseMvvmFragment : BaseFragment() {

    private val rxDelegation = RxDelegation()


    override
    fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onSetupViewModel()
    }

    open fun onSetupViewModel() {

    }

    fun <VM : ViewModel> getViewModel(viewModelClass: Class<VM>): VM
            = ViewModelProviders.of(this).get(viewModelClass)

    fun <VM : ViewModel> getViewModel(key: String, viewModelClass: Class<VM>): VM
            = ViewModelProviders.of(this).get(key, viewModelClass)


    fun <VM : ViewModel> getSharedViewModel(viewModelClass: Class<VM>): VM
            = ViewModelProviders.of(activity).get(viewModelClass)


    override
    fun onDestroy() {
        super.onDestroy()
        rxDelegation.clearAllDisposables()
    }

    fun addDisposable(d: Disposable) {
        rxDelegation.addDisposable(d)
    }

}

