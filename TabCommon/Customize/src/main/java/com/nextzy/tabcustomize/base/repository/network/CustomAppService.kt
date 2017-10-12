package com.nextzy.tabcustomize.base.repository.network

import com.nextzy.library.base.mvvm.layer3Repository.network.base.BaseApiCreator
import okhttp3.OkHttpClient

/**
 * Created by「 The Khaeng 」on 29 Sep 2017 :)
 */


abstract class CustomAppService<T> (apiClass: Class<T>)
    : BaseApiCreator<T>(apiClass) {

    override
    fun getBaseUrl(): String = super.getBaseUrl()

    override
    fun getClient(): OkHttpClient {
        return super.getClient()
    }

}
