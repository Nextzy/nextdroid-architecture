package com.nextzy.tabcustomize.base.delegation

import android.support.v4.view.ViewCompat
import android.view.View
import android.view.animation.OvershootInterpolator


/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

class DefaultAnimationDelegate : DefaultAnimationDelegateListener {

    var isPendingIntroAnimation: Boolean = false

    protected val defaultOvershootInterpolator: OvershootInterpolator = OvershootInterpolator(1f)

    override
    fun setTransitionName(target: View, transitionName: String) {
        ViewCompat.setTransitionName(target, transitionName)
    }

}
