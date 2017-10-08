package com.nextzy.library.base.mvvm.layer1View

import android.arch.lifecycle.ViewModelProviders
import com.nextzy.library.base.delegate.RxDelegation
import com.nextzy.library.base.mvvm.exception.ViewModelNotNullException
import com.nextzy.library.base.mvvm.exception.ViewModelNotSetupException
import com.nextzy.library.base.mvvm.layer2ViewModel.BaseViewModel
import io.reactivex.disposables.Disposable


/**
 * Created by「 The Khaeng 」on 24 Aug 2017 :)
 */

abstract class BaseMvvmFragment<VM : BaseViewModel> : DialogHelperFragment() {

    private val rxDelegation = RxDelegation()

    val viewModelShared: VM?
        get() {
            if (setupViewModel() == null) throw ViewModelNotSetupException()
            return if (activity != null) {
                ViewModelProviders.of(activity)
                        .get(setupViewModel())
            } else null
        }

    val viewModel: VM
        get() {
            if (setupViewModel() == null) throw ViewModelNotSetupException()
            return ViewModelProviders.of(this)
                    .get(setupViewModel())
        }

    abstract fun setupViewModel(): Class<VM>?

    fun getViewModel(viewModelClass: Class<VM>): VM {
        if (setupViewModel() == null) throw ViewModelNotNullException()
        return ViewModelProviders.of(this)
                .get(viewModelClass)
    }

    override
    fun onDestroy() {
        super.onDestroy()
        rxDelegation.clearAllDisposables()
    }

    fun addDisposable(d: Disposable) {
        rxDelegation.addDisposable(d)
    }

}

