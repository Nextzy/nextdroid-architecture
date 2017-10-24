package com.nextzy.tabcustomize.base.mvvm.animation

import android.app.Activity
import android.app.ActivityManager
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.transition.Transition
import android.view.Menu
import android.view.View
import com.nextzy.library.R
import com.nextzy.library.base.mvvm.layer1View.BaseMvvmActivity
import com.nextzy.tabcustomize.base.delegation.DefaultSnackbarDelegate
import com.nextzy.tabcustomize.base.delegation.DefaultSnackbarInterface

/**
 * Created by「 The Khaeng 」on 18 Sep 2017 :)
 */

abstract class AnimationHelperMvvmActivity
    : BaseMvvmActivity(),
        DefaultSnackbarInterface {

    private lateinit var snackbarDelegate: DefaultSnackbarDelegate
    private var onStartCount: Int = 0

    val isFinishAfterTransition: Boolean = true

    var isPendingIntroAnimation: Boolean = false

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        snackbarDelegate = DefaultSnackbarDelegate(this)

        if (savedInstanceState == null) {
            isPendingIntroAnimation = true
            setOverridePendingStartTransition()
        } else { // already created so reverse animation
            onStartCount = 2
        }
    }

    override
    fun onStart() {
        super.onStart()
        if (onStartCount > 1) {
            setOverridePendingEndTransition()
        } else if (onStartCount == 1) {
            onStartCount++
        }

    }

    open fun setOverridePendingStartTransition() {
        this.overridePendingTransition(
                R.anim.slide_right_in,
                R.anim.slide_right_out)

    }

    open fun setOverridePendingEndTransition() {
        this.overridePendingTransition(
                R.anim.slide_left_in,
                R.anim.slide_left_out)
    }

    fun overridePendingDefaultTransition() {}

    fun overridePendingFadeTransition() {
        this.overridePendingTransition(
                R.anim.slide_right_in,
                R.anim.slide_right_out)
    }

    override
    fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        if (isPendingIntroAnimation) {
            isPendingIntroAnimation = false
            startIntroAnimation()
        }
        return true
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected fun setTaskDescription(label: String, icon: Bitmap?, colorPrimary: Int) {
        val taskDesc = ActivityManager.TaskDescription(
                label,
                icon,
                colorPrimary)
        setTaskDescription(taskDesc)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    fun addEnterTransition(transition: Transition) {
        window.sharedElementEnterTransition = transition

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    fun addExitTransition(transition: Transition) {
        window.sharedElementReturnTransition = transition
    }


    override
    fun backPressedFragment() {
        setResult(Activity.RESULT_OK)
        if (isFinishAfterTransition) {
            supportFinishAfterTransition()
        } else {
            finish()
        }
    }

    fun onBackPressed(data: Bundle) {
        val resultData = Intent()
        resultData.putExtras(data)
        setResult(Activity.RESULT_OK, resultData)
        if (isFinishAfterTransition) {
            supportFinishAfterTransition()
        } else {
            finish()
        }
    }

    fun showFocusView(view: View) {
        // TODO1: 7/11/2017 AD implement pulse animation
    }

    /**
     * Start intro animation.
     */
    protected fun startIntroAnimation() {}

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