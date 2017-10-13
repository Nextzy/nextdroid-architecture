package com.nextzy.tabcustomize.template.mvvm.repository.network.api

import com.nextzy.tabcustomize.base.repository.network.DefaultAppService

/**
 * Created by「 The Khaeng 」on 29 Sep 2017 :)
 */


class CustomApiService private constructor()
    : DefaultAppService<CustomApi>(CustomApi::class.java) {

    companion object {
        val newInstance
            get() = CustomApiService()
    }
}