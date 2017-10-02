package com.nextzy.nextdroidapp

import android.support.v4.app.FragmentManager

import com.facebook.stetho.Stetho
import th.co.thekhaeng.waterbottleminder.DebugTree

import timber.log.Timber

/**
* Created by「 The Khaeng 」on 28 Aug 2017 :)
*/

class NextDroidApplication : MainApplication() {

    override
    fun onCreate() {
        super.onCreate()
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                                  .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                                  .build())
        Timber.plant(DebugTree())
        FragmentManager.enableDebugLogging(true)
    }


    override
    fun onTerminate() {
        super.onTerminate()
    }

}
