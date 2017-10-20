package com.nextzy.library.base.mvvm.layer3Repository.network.base

import okhttp3.Interceptor
import okhttp3.OkHttpClient

/**
 * Created by「 The Khaeng 」on 29 Sep 2017 :)
 */

object DefaultClient : BaseClient() {

    override
    fun configBuilder(builder: OkHttpClient.Builder) {
        super.configBuilder(builder)
    }

    override
    fun setupInterceptorList(): List<Interceptor> {
        return super.setupInterceptorList()
    }

}
