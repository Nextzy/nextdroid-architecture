package com.nextzy.nextdroidapp.repository.network.api

import com.nextzy.nextdroidapp.repository.network.model.reponse.PictureResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by「 The Khaeng 」on 29 Sep 2017 :)
 */
interface PictureApi {

    @POST(Url.URL_LIST)
    fun loadPictureList(): Single<Response<PictureResponse>>

    @POST(Url.URL_LIST_AFTER)
    fun loadPictureListAfterId(@Path("id") id: Int): Single<Response<PictureResponse>>

    @POST(Url.URL_LIST_BEFORE)
    fun loadPictureListBeforeId( @Path("id") id: Int ): Single<Response<PictureResponse>>
}