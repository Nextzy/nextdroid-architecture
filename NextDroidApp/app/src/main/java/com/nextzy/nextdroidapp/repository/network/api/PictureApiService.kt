package com.nextzy.nextdroidapp.repository.network.api

import com.nextzy.tabcustomize.base.repository.network.DefaultAppService

/**
 * Created by「 The Khaeng 」on 29 Sep 2017 :)
 */


class PictureApiService private constructor()
    : DefaultAppService<PictureApi>(PictureApi::class.java) {

    companion object {
        val newInstance
            get() = PictureApiService()
    }
}