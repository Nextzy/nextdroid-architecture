package com.nextzy.library.base.delegate

import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.view.View

/**
 * Created by「 The Khaeng 」on 19 Aug 2017 :)
 */

interface DefaultSnackbarInterface {

    fun setSnackbarTargetView(target: View)

    fun showSnackbarCustom(@ColorRes colorId: Int,
                           @DrawableRes iconId: Int,
                           message: String,
                           duration: Int = DefaultSnackbarDelegate.DEFAULT_DURATION)

    fun showSnackbarCustomDismiss(@ColorRes colorId: Int,
                                  @DrawableRes iconId: Int,
                                  message: String)


    fun showSnackbarSuccess(message: String,
                            duration: Int = DefaultSnackbarDelegate.DEFAULT_DURATION)


    fun showSnackbarWarning(message: String,
                            duration: Int = DefaultSnackbarDelegate.DEFAULT_DURATION)


    fun showSnackbarError(message: String,
                          duration: Int = DefaultSnackbarDelegate.DEFAULT_DURATION)


    fun showSnackbarInfo(message: String,
                         duration: Int = DefaultSnackbarDelegate.DEFAULT_DURATION)


    fun showSnackbarSuccessDismiss(message: String)


    fun showSnackbarWarningDismiss(message: String)


    fun showSnackbarErrorDismiss(message: String)


    fun showSnackbarInfoDismiss(message: String)

}
