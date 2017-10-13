package com.nextzy.tabcustomize.template.mvvm.repository.network.api

import com.nextzy.tabcustomize.template.mvvm.repository.network.model.reponse.CustomResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by「 The Khaeng 」on 29 Sep 2017 :)
 */
interface CustomApi {

    @GET(CustomUrl.URL_TEST)
    fun getTestResponse(): Single<Response<CustomResponse>>
}