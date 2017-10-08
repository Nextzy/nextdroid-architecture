package com.nextzy.library.base.delegate

import android.app.Activity
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.View

import com.danimahardhika.cafebar.CafeBar
import com.danimahardhika.cafebar.CafeBarDuration
import com.danimahardhika.cafebar.CafeBarTheme
import com.nextzy.library.R

/**
 * Created by「 The Khaeng 」on 20 Aug 2017 :)
 */

class DefaultSnackbarDelegate : DefaultSnackbarInterface {

    companion object {
        val VERY_SHORT = 1000
        val SHORT = CafeBarDuration.SHORT.duration
        val MEDIUM = CafeBarDuration.MEDIUM.duration
        val DEFAULT_DURATION = CafeBarDuration.MEDIUM.duration
    }

    private var targetView: View? = null
    private var activity: Activity
    private var fragment: Fragment? = null

    private val view: View?
        get() = when {
            targetView != null -> targetView
            fragment != null -> fragment?.view
            else -> activity.findViewById(android.R.id.content)
        }

    constructor(activity: Activity) {
        this.activity = activity
    }

    constructor(fragment: Fragment) {
        this.activity = fragment.activity
        this.fragment = fragment
    }


    override
    fun setSnackbarTargetView(target: View?) {
        this.targetView = target
    }

    override
    fun showSnackbarCustom(@ColorRes colorId: Int,
                           @DrawableRes iconId: Int,
                           message: String,
                           duration: Int) {
        createCustomBuilder(view,
                            colorId,
                            iconId)
                .duration(duration)
                .autoDismiss(true)
                .content(message)
                .show()
    }

    override
    fun showSnackbarCustomDismiss(@ColorRes colorId: Int,
                                  @DrawableRes iconId: Int,
                                  message: String) {
        createCustomBuilder(view,
                            colorId,
                            iconId)
                .autoDismiss(false)
                .content(message)
                .neutralText(R.string.dismiss)
                .neutralColor(ContextCompat.getColor(activity, R.color.md_white))
                .show()
    }

    override
    fun showSnackbarSuccess(message: String, duration: Int) {
        createSuccessBuilder(view)
                .duration(duration)
                .autoDismiss(true)
                .content(message)
                .show()
    }


    override
    fun showSnackbarInfo(message: String, duration: Int) {
        createInfoBuilder(view)
                .duration(duration)
                .autoDismiss(true)
                .content(message)
                .show()
    }


    override
    fun showSnackbarWarning(message: String, duration: Int) {
        createWarningBuilder(view)
                .duration(duration)
                .autoDismiss(true)
                .content(message)
                .show()
    }


    override
    fun showSnackbarError(message: String, duration: Int) {
        createErrorBuilder(view)
                .duration(duration)
                .autoDismiss(true)
                .content(message)
                .show()
    }


    override
    fun showSnackbarSuccessDismiss(message: String) {
        createSuccessBuilder(view)
                .autoDismiss(false)
                .content(message)
                .neutralText(R.string.dismiss)
                .neutralColor(ContextCompat.getColor(activity, R.color.md_white))
                .show()
    }

    override
    fun showSnackbarInfoDismiss(message: String) {
        createInfoBuilder(view)
                .autoDismiss(false)
                .content(message)
                .neutralText(R.string.dismiss)
                .neutralColor(ContextCompat.getColor(activity, R.color.snackbar_success))
                .show()
    }

    override
    fun showSnackbarWarningDismiss(message: String) {
        createWarningBuilder(view)
                .autoDismiss(false)
                .content(message)
                .neutralText(R.string.dismiss)
                .neutralColor(ContextCompat.getColor(activity, R.color.md_white))
                .show()
    }

    override
    fun showSnackbarErrorDismiss(message: String) {
        createErrorBuilder(view)
                .autoDismiss(false)
                .content(message)
                .neutralText(R.string.dismiss)
                .neutralColor(ContextCompat.getColor(activity, R.color.ios_orange_light))
                .show()
    }

    private fun createDefaultBuilder(view: View?): CafeBar.Builder {
        return CafeBar.builder(activity)
                .to(view)
                .fitSystemWindow()
                .typeface("ProductSans-Regular.ttf", "ProductSans-Bold.ttf")
    }

    private fun createSuccessBuilder(view: View?): CafeBar.Builder {
        return createCustomBuilder(view,
                                   R.color.snackbar_success,
                                   R.drawable.ic_snackbar_success_white)
    }

    private fun createInfoBuilder(view: View?): CafeBar.Builder {
        return createCustomBuilder(view,
                                   R.color.snackbar_info,
                                   R.drawable.ic_snackbar_info_white)
    }

    private fun createWarningBuilder(view: View?): CafeBar.Builder {
        return createCustomBuilder(view,
                                   R.color.snackbar_warning,
                                   R.drawable.ic_snackbar_warning_white)
    }

    private fun createErrorBuilder(view: View?): CafeBar.Builder {
        return createCustomBuilder(view,
                                   R.color.snackbar_error,
                                   R.drawable.ic_snackbar_error_white)
    }

    private fun createCustomBuilder(view: View?,
                                    @ColorRes colorId: Int,
                                    @DrawableRes iconId: Int): CafeBar.Builder {
        return createDefaultBuilder(view)
                .theme(CafeBarTheme.Custom(
                        ContextCompat.getColor(
                                activity,
                                colorId)))
                .icon(iconId, false)
    }

}
