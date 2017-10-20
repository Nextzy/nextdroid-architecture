package com.nextzy.nextdroidapp

import android.support.multidex.MultiDexApplication
import cat.ereza.customactivityoncrash.config.CaocConfig
import com.bumptech.glide.annotation.GlideModule
import com.nextzy.nextdroidapp.module.crash.CrashActivity
import com.nextzy.tabcustomize.base.repository.database.realm.CustomDatabase
import com.orhanobut.hawk.Hawk

/**
 * Created by「 The Khaeng 」on 28 Aug 2017 :)
 */

abstract class MainApplication : MultiDexApplication() {

    companion object {
        val crashConfig: CaocConfig
            get() = crashConfigBuilder.get()

        private val crashConfigBuilder: CaocConfig.Builder
            get() = CaocConfig.Builder.create()
                    .showErrorDetails(true) //default: true
                    .showRestartButton(false) //default: true
                    .trackActivities(true) //default: false
                    .minTimeBetweenCrashesMs(0) //default: 3000
                    .restartActivity(CrashActivity::class.java) //default: null (your app's launch activity)
                    .errorActivity(CrashActivity::class.java)
    }

    override
    fun onCreate() {
        super.onCreate()
        setupSetting()
        setupCrashActivity()
        setupDatabase()
    }

    private fun setupDatabase() {
        Hawk.init(this).build()
        CustomDatabase.instance.initDatabase(this)
    }

    private fun setupCrashActivity() {
        crashConfigBuilder.apply()
    }

    private fun setupSetting() {
//        InitSetting.init(this)
//                .ifFirstRunApplication() // important
//                .persistString(R.string.key_value, resources.getStringArray(R.array.value_lise)[0])
//                .persistBoolean(R.string.key_setting_vibrate, true)
    }

}
