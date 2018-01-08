package com.nextzy.tabcustomize.base.repository.network

import com.google.gson.GsonBuilder
import com.nextzy.library.base.mvvm.layer3Repository.network.base.BaseApiCreator
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by「 The Khaeng 」on 29 Sep 2017 :)
 */


abstract class DefaultAppService<T>(apiClass: Class<T>)
    : BaseApiCreator<T>(apiClass) {

    override
    fun getBaseUrl(): String = "https://nuuneoi.com/courses/500px/"

    override
    fun addConverter(): Converter.Factory? {
        return GsonConverterFactory.create(
                GsonBuilder()
                        .setPrettyPrinting()
                        .setDateFormat( "yyy-MM-dd'T'HH:mm:ssZ" )
                        .create())
    }

    override
    fun getOverrideClientTimeout(): Long {
        return 10000
    }

}
