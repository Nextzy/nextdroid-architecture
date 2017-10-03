package com.nextzy.library.base.mvvm.layer3Repository.network.base

import okhttp3.Interceptor
import okhttp3.OkHttpClient

/**
 * Created by「 The Khaeng 」on 29 Sep 2017 :)
 */

class DefaultClient : BaseClient() {

    companion object {
        lateinit var instance: DefaultClient
            private set
    }

    init {
        instance = DefaultClient()
    }

    override
    fun configBuilder(builder: OkHttpClient.Builder) {
        super.configBuilder(builder)
    }

    override
    fun setupInterceptorList(): List<Interceptor> {
        return super.setupInterceptorList()
    }

}
