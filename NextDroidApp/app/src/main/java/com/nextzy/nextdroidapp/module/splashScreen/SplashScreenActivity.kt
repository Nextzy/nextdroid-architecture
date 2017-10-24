package com.nextzy.nextdroidapp.module.splashScreen

import com.nextzy.library.extension.view.delay
import com.nextzy.nextdroidapp.R
import com.nextzy.nextdroidapp.module.main.MainActivity
import com.nextzy.tabcustomize.base.mvvm.CustomMvvmActivity
import io.reactivex.functions.Action

/**
 * Created by「 The Khaeng 」on 18 Sep 2017 :)
 */

class SplashScreenActivity : CustomMvvmActivity() {

    private lateinit var viewModel: SplashScreenViewModel

    companion object {
        private const val ONE_SECOND: Long = 1000
        private const val TEN_SECOND: Long = ONE_SECOND * 10
    }

    override
    fun onSetupViewModel(){
        super.onSetupViewModel()
        viewModel = getViewModel( SplashScreenViewModel::class.java)
    }

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
    fun onSetupView() {
        super.onSetupView()
        delay(Action { openActivityWithFinish(MainActivity::class.java) },
              ONE_SECOND)
    }

}

