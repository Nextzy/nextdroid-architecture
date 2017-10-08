package com.nextzy.tabcustomize.network

import com.nextzy.library.base.mvvm.layer3Repository.network.base.BaseApiCreator
import okhttp3.OkHttpClient

/**
 * Created by「 The Khaeng 」on 29 Sep 2017 :)
 */


class CustomService private constructor()
    : BaseApiCreator<CustomApi>(CustomApi::class.java) {

    companion object {
        val newInstance
            get() = CustomService()
    }

    override
    fun getBaseUrl(): String = super.getBaseUrl()

    override
    fun getClient(): OkHttpClient {
        return super.getClient()
    }

}
