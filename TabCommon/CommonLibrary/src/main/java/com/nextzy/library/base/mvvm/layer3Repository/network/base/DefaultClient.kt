package com.nextzy.library.base.mvvm.layer3Repository.network.base

import okhttp3.Interceptor
import okhttp3.OkHttpClient

/**
 * Created by「 The Khaeng 」on 29 Sep 2017 :)
 */

class DefaultClient : BaseClient() {
    private object Holder {
        val INSTANCE = DefaultClient()
    }

    companion object {
        val instance: DefaultClient by lazy { Holder.INSTANCE }
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
