package com.nextzy.library.base.mvvm.layer1View

import android.arch.lifecycle.ViewModelProviders

import com.nextzy.library.base.delegate.RxDelegation
import com.nextzy.library.base.mvvm.exception.ViewModelNotNullException
import com.nextzy.library.base.mvvm.exception.ViewModelNotSetupException
import com.nextzy.library.base.mvvm.layer2ViewModel.BaseViewModel

import io.reactivex.disposables.Disposable

/**
 * Created by「 The Khaeng 」on 27 Aug 2017 :)
 */

abstract class BaseMvvmActivity<VM : BaseViewModel> : DialogHelperActivity() {

    private val rxDelegation = RxDelegation()

    val viewModel: VM
        get() {
            if (setupViewModel() == null) throw ViewModelNotSetupException()
            return ViewModelProviders.of(this)
                    .get(setupViewModel())
        }

    abstract fun setupViewModel(): Class<VM>?

   override
   fun onDestroy() {
        super.onDestroy()
        rxDelegation.clearAllDisposables()
    }

    fun getViewModel(viewModelClass: Class<VM>): VM {
        if (setupViewModel() == null) throw ViewModelNotNullException()
        return ViewModelProviders.of(this)
                .get(viewModelClass)
    }

    fun addDisposable(d: Disposable) {
        rxDelegation.addDisposable(d)
    }

}

