package com.nextzy.tabcustomize.template.mvvm.repository.network

import com.nextzy.tabcustomize.base.repository.network.CustomAppService

/**
 * Created by「 The Khaeng 」on 29 Sep 2017 :)
 */


class CustomApiService private constructor()
    : CustomAppService<CustomApi>(CustomApi::class.java) {

    companion object {
        val newInstance
            get() = CustomApiService()
    }
}