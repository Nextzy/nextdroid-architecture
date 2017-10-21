package com.nextzy.nextdroidapp.repository.network.api

import com.nextzy.nextdroidapp.repository.network.model.reponse.PhotoResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by「 The Khaeng 」on 29 Sep 2017 :)
 */
interface PhotoApi {

    @POST(Url.URL_LIST)
    fun loadPhotoList(): Single<Response<PhotoResponse>>

    @POST(Url.URL_LIST_AFTER)
    fun loadPhotoListAfterId(@Path("id") id: Int): Single<Response<PhotoResponse>>

    @POST(Url.URL_LIST_BEFORE)
    fun loadPhotoListBeforeId(@Path("id") id: Int): Single<Response<PhotoResponse>>
}