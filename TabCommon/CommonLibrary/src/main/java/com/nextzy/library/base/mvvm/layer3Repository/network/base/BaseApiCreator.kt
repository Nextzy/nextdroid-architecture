package com.nextzy.library.base.mvvm.layer3Repository.network.base


import com.nextzy.nextwork.engine.NextworkApiCreator

import okhttp3.OkHttpClient

/**
 * Created by「 The Khaeng 」on 03 Oct 2017 :)
 */

abstract class BaseApiCreator<T>(apiClass: Class<T>) : NextworkApiCreator<T>(apiClass) {


    companion object {
        val BASE_URL = ""
    }

    override
    fun getBaseUrl(): String {
        return BASE_URL
    }

    override
    fun getClient(): OkHttpClient {
        return DefaultClient.instance.okHttpClient
    }
}
