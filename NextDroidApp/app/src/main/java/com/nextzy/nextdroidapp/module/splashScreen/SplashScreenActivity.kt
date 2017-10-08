package com.nextzy.nextdroidapp.module.splashScreen

import com.nextzy.tabcustomize.base.mvvm.CustomMvvmActivity
import com.nextzy.library.extension.view.delay
import com.nextzy.nextdroidapp.R
import com.nextzy.nextdroidapp.module.main.MainActivity
import io.reactivex.functions.Action

/**
 * Created by「 The Khaeng 」on 18 Sep 2017 :)
 */

class SplashScreenActivity : CustomMvvmActivity<SplashScreenViewModel>() {


    override
    fun setupViewModel(): Class<SplashScreenViewModel>? = SplashScreenViewModel::class.java

    private val ONE_SECOND: Long = 1000
    private val TEN_SECOND: Long = ONE_SECOND * 10


    override
    fun setupLayoutView(): Int = R.layout.activity_splash_screen


    override
    fun setOverridePendingStartTransition() {
        super.setOverridePendingStartTransition()
        this.overridePendingTransition(
                R.anim.activity_open_enter,
                R.anim.activity_close_exit)
    }

    override
    fun setOverridePendingEndTransition() {
        super.setOverridePendingEndTransition()
        this.overridePendingTransition(
                R.anim.activity_open_enter,
                R.anim.activity_close_exit)
    }


    override
    fun setupView() {
        delay(Action { openActivityWithFinish(MainActivity::class.java) },
              ONE_SECOND)
    }

}

