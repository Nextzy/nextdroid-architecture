package com.nextzy.tabcustomize.base.mvvm.layer1View.dialog

import com.nextzy.library.base.mvvm.layer1View.BaseMvvmFragment
import com.nextzy.tabcustomize.base.delegation.DefaultDialogDelegation

/**
* Created by「 The Khaeng 」on 02 Oct 2017 :)
*/

abstract class DialogHelperMvvmFragment: BaseMvvmFragment()  {

    val dialogDelegation = DefaultDialogDelegation()

    fun showLoading() {

    }

    fun hideLoading() {

    }
}