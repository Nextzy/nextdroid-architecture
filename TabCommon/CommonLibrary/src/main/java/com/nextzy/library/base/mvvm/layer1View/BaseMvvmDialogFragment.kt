package com.nextzy.library.base.mvvm.layer1View

import android.arch.lifecycle.ViewModelProviders
import com.nextzy.library.base.delegate.DefaultSnackbarInterface
import com.nextzy.library.base.mvvm.exception.ShareViewModelNotCreated
import com.nextzy.library.base.mvvm.exception.ViewModelNotNullException
import com.nextzy.library.base.mvvm.exception.ViewModelNotSetupException
import com.nextzy.library.base.mvvm.layer2ViewModel.BaseDialogViewModel
import com.nextzy.setting.view.util.SettingPreferenceInterface
import timber.log.Timber

/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

abstract class BaseMvvmDialogFragment<VM : BaseDialogViewModel>
    : BaseDialogFragment(),
      DefaultSnackbarInterface,
      SettingPreferenceInterface {


    val viewModelShared: VM?
        get() {
            if (setupViewModel() == null) throw ViewModelNotSetupException()
            if (activity != null) {
                return ViewModelProviders.of(activity)
                        .get(setupViewModel())
            }
            Timber.w("getViewModelShared: ", ShareViewModelNotCreated())
            return null
        }

    val viewModel: VM
        get() {
            if (setupViewModel() == null) throw ViewModelNotSetupException()
            return ViewModelProviders.of(this)
                    .get(setupViewModel())
        }


    fun getViewModel(viewModelClass: Class<VM>): VM {
        if (setupViewModel() == null) throw ViewModelNotNullException()
        return ViewModelProviders.of(this)
                .get(viewModelClass)
    }


    abstract fun setupViewModel(): Class<VM>?

}