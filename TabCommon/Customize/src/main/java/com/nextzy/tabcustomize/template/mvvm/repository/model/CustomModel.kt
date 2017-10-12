package com.nextzy.tabcustomize.template.mvvm.repository.model

import com.nextzy.nextwork.engine.model.NextworkModel

/**
 * Created by「 The Khaeng 」on 01 Sep 2017 :)
 */

data class CustomModel(var id: String?=null)
    :NextworkModel {

    override
    fun shouldFetch(): Boolean {
        return true
    }
}