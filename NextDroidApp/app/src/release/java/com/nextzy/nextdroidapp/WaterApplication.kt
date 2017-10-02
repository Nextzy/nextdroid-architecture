package com.nextzy.nextdroidapp

import timber.log.Timber

/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

class WaterApplication : MainApplication() {

    override
    fun onCreate() {
        super.onCreate()
        Timber.plant(ReleaseTree())
        //        Timber.plant(new DebugTree());
    }

    override
    fun onTerminate() {
        super.onTerminate()
    }

}
