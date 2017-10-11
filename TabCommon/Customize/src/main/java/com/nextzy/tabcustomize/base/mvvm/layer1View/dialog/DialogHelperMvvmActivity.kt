package com.nextzy.tabcustomize.base.mvvm.layer1View.dialog

import com.nextzy.tabcustomize.base.delegation.DefaultDialogDelegation
import com.nextzy.tabcustomize.base.mvvm.layer1View.animation.AnimationHelperMvvmActivity

/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

abstract class DialogHelperMvvmActivity: AnimationHelperMvvmActivity()  {

    val dialogDelegation = DefaultDialogDelegation()

    fun showLoading() {

    }

    fun hideLoading() {

    }
}