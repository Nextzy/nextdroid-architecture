package com.nextzy.tabcustomize.base.mvvm.animation

import android.view.View
import com.nextzy.library.base.mvvm.layer1View.BaseMvvmBottomSheetFragment
import com.nextzy.tabcustomize.base.delegation.DefaultSnackbarDelegate
import com.nextzy.tabcustomize.base.delegation.DefaultSnackbarInterface

/**
 * Created by「 The Khaeng 」on 26 Aug 2017 :)
 */

abstract class AnimationHelperBottomSheetFragment
    : BaseMvvmBottomSheetFragment(),
        DefaultSnackbarInterface {

    private lateinit var snackbarDelegate: DefaultSnackbarDelegate


    override
    fun setContentView(contentView: View) {
        super.setContentView(contentView)
        snackbarDelegate.setSnackbarTargetView(contentView)
    }


    override
    fun setSnackbarTargetView(target: View?) {
        snackbarDelegate.setSnackbarTargetView(target)
    }

    override
    fun showSnackbarCustom(colorId: Int, iconId: Int, message: String, duration: Int) {
        snackbarDelegate.showSnackbarCustom(colorId, iconId, message, duration)
    }

    override
    fun showSnackbarCustomDismiss(colorId: Int, iconId: Int, message: String) {
        snackbarDelegate.showSnackbarCustomDismiss(colorId, iconId, message)
    }

    override
    fun showSnackbarSuccess(message: String, duration: Int) {
        snackbarDelegate.showSnackbarSuccess(message, duration)
    }

    override
    fun showSnackbarWarning(message: String, duration: Int) {
        snackbarDelegate.showSnackbarWarning(message, duration)
    }

    override
    fun showSnackbarError(message: String, duration: Int) {
        snackbarDelegate.showSnackbarError(message, duration)
    }

    override
    fun showSnackbarInfo(message: String, duration: Int) {
        snackbarDelegate.showSnackbarInfo(message, duration)
    }

    override
    fun showSnackbarSuccessDismiss(message: String) {
        snackbarDelegate.showSnackbarSuccessDismiss(message)
    }

    override
    fun showSnackbarWarningDismiss(message: String) {
        snackbarDelegate.showSnackbarWarningDismiss(message)
    }

    override
    fun showSnackbarErrorDismiss(message: String) {
        snackbarDelegate.showSnackbarErrorDismiss(message)
    }

    override
    fun showSnackbarInfoDismiss(message: String) {
        snackbarDelegate.showSnackbarInfoDismiss(message)
    }

}