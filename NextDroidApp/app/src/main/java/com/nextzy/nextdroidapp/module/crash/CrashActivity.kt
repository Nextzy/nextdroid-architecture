package com.nextzy.nextdroidapp.module.crash

import android.content.Intent
import android.util.Log
import cat.ereza.customactivityoncrash.CustomActivityOnCrash
import com.nextzy.nextdroidapp.MainApplication
import com.nextzy.nextdroidapp.R
import com.nextzy.nextdroidapp.module.main.MainActivity
import com.nextzy.tabcustomize.base.mvvm.layer1View.CustomMvvmActivity
import kotlinx.android.synthetic.main.activity_crash.btn_restart as btnRestart


/**
* Created by「 The Khaeng 」on 03 Oct 2017 :)
*/

class CrashActivity : CustomMvvmActivity() {

    override
    fun setupLayoutView(): Int = R.layout.activity_crash


    override
    fun initialize() {
        repeatShowErrorStackTrace()
    }

    override
    fun setupView() {
        btnRestart.setOnClickListener({ restartApplication() })
    }


    private fun restartApplication() {
        val intent = Intent(this, MainActivity::class.java)
        CustomActivityOnCrash.restartApplicationWithIntent(
                this,
                intent,
                MainApplication.crashConfig)
    }

    private fun repeatShowErrorStackTrace() {
        val stackTrace: String? = CustomActivityOnCrash.getStackTraceFromIntent(intent)
        if (stackTrace != null) {
            Log.e(this.javaClass.simpleName, stackTrace)
        }
    }

}

