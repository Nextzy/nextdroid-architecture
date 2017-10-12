package com.nextzy.tabcustomize.base.mvvm.animation

import android.os.Bundle
import android.support.transition.Transition
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import com.nextzy.library.base.delegate.DefaultSnackbarDelegate
import com.nextzy.library.base.delegate.DefaultSnackbarInterface
import com.nextzy.library.base.mvvm.layer1View.BaseMvvmFragment
import com.nextzy.tabcustomize.base.delegation.DefaultAnimationDelegate
import com.nextzy.tabcustomize.base.delegation.DefaultAnimationDelegateListener

/**
 * Created by「 The Khaeng 」on 18 Sep 2017 :)
 */

abstract class AnimationHelperMvvmFragment
    : BaseMvvmFragment(),
      DefaultAnimationDelegateListener,
      DefaultSnackbarInterface {

    private lateinit var snackbarDelegate: DefaultSnackbarDelegate
    private val animationDelegate = DefaultAnimationDelegate()

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        snackbarDelegate = DefaultSnackbarDelegate(this)
        if (savedInstanceState == null) {
            animationDelegate.isPendingIntroAnimation = true
        }
    }

    override
    fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        if (animationDelegate.isPendingIntroAnimation) {
            animationDelegate.isPendingIntroAnimation = false
            startIntroAnimation()
        }
    }

    fun showFocusView(view: View) {
        // TODO1: 7/11/2017 AD implement pulse animation
    }


    fun addEnterTransition(transition: Transition) {
        sharedElementEnterTransition = transition
    }

    fun addExitTransition(transition: Transition) {
        sharedElementReturnTransition = transition
    }

    override
    fun setTransitionName(target: View, transitionName: String) {
        animationDelegate.setTransitionName(target, transitionName)
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