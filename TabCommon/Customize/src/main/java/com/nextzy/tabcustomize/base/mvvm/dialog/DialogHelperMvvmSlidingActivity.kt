package com.nextzy.tabcustomize.base.mvvm.dialog

import com.nextzy.library.base.mvvm.layer1View.BaseMvvmSlidingActivity
import com.nextzy.tabcustomize.base.delegation.DefaultDialogDelegation

/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

abstract class DialogHelperMvvmSlidingActivity :BaseMvvmSlidingActivity(){

    val dialogDelegation = DefaultDialogDelegation()

    fun showLoading() {

    }

    fun hideLoading() {

    }
}