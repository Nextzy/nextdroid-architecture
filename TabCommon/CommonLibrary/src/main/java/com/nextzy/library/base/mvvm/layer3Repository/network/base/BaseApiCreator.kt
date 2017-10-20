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
    fun getBaseUrl(): String = BASE_URL

    override
    fun getClient(): OkHttpClient = DefaultClient.okHttpClient

//    override
//    fun getAdapterFactory(): CallAdapter.Factory = LiveDataCallAdapterFactory.create()
}
