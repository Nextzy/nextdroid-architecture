package com.nextzy.tabcustomize.template.mvvm.repository.network

import android.arch.lifecycle.LiveData
import com.nextzy.nextwork.engine.model.NextworkResponse
import com.nextzy.tabcustomize.template.mvvm.repository.network.response.CustomResponse
import retrofit2.http.GET

/**
 * Created by「 The Khaeng 」on 29 Sep 2017 :)
 */
interface CustomApi {

    @GET(CustomUrl.URL_TEST)
    fun getTestResponse(): LiveData<NextworkResponse<CustomResponse>>
}