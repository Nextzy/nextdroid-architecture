package com.nextzy.nextdroidapp.repository.network

import android.arch.lifecycle.LiveData
import com.nextzy.nextdroidapp.repository.network.api.PictureApiService
import com.nextzy.nextdroidapp.repository.network.model.reponse.PictureResponse
import com.nextzy.nextwork.engine.NextworkLiveDataConverter
import com.nextzy.nextwork.operator.NextworkLogError
import com.nextzy.tabcustomize.base.repository.network.ConvertToDefaultResponse
import com.nextzy.tabcustomize.base.repository.network.DefaultResponse
import retrofit2.Response

/**
 * Created by「 The Khaeng 」on 12 Oct 2017 :)
 */

class PictureApiManager {

    private object Holder {
        val INSTANCE = PictureApiManager()
    }

    companion object {
        val SERVICE_LOAD_PICTURE_LIST = "service_load_picture_list"
        val SERVICE_LOAD_PICTURE_LIST_AFTER_ID = "service_load_picture_list_after_id"
        val SERVICE_LOAD_PICTURE_LIST_BEFORE_ID = "service_load_picture_list_before_id"
        val instance: PictureApiManager by lazy { Holder.INSTANCE }
    }


    fun getPictureList(): LiveData<DefaultResponse<PictureResponse>> {
        return NextworkLiveDataConverter.convert(
                PictureApiService.newInstance
                        .createApi()
                        .loadPictureList()
                        .onErrorResumeNext(NextworkLogError(SERVICE_LOAD_PICTURE_LIST))
                        .compose(ConvertToDefaultResponse<Response<PictureResponse>, DefaultResponse<PictureResponse>>()))
    }

    fun getPictureListAfterId(id: Int): LiveData<DefaultResponse<PictureResponse>> {
        return NextworkLiveDataConverter.convert(
                PictureApiService.newInstance
                        .createApi()
                        .loadPictureListAfterId(id)
                        .onErrorResumeNext(NextworkLogError(SERVICE_LOAD_PICTURE_LIST_AFTER_ID))
                        .compose(ConvertToDefaultResponse<Response<PictureResponse>, DefaultResponse<PictureResponse>>()))
    }

    fun getPictureListBeforeId(id: Int): LiveData<DefaultResponse<PictureResponse>> {
        return NextworkLiveDataConverter.convert(
                PictureApiService.newInstance
                        .createApi()
                        .loadPictureListBeforeId(id)
                        .onErrorResumeNext(NextworkLogError(SERVICE_LOAD_PICTURE_LIST_BEFORE_ID))
                        .compose(ConvertToDefaultResponse<Response<PictureResponse>, DefaultResponse<PictureResponse>>()))
    }

}
