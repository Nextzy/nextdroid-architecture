package com.nextzy.nextdroidapp.repository.network

import android.arch.lifecycle.LiveData
import com.nextzy.nextdroidapp.repository.network.api.PhotoApiService
import com.nextzy.nextdroidapp.repository.network.model.reponse.PhotoResponse
import com.nextzy.nextwork.engine.NextworkLiveDataConverter
import com.nextzy.nextwork.operator.NextworkLogError
import com.nextzy.tabcustomize.base.repository.network.ConvertToDefaultResponse
import com.nextzy.tabcustomize.base.repository.network.DefaultResponse
import retrofit2.Response

/**
 * Created by「 The Khaeng 」on 12 Oct 2017 :)
 */

class PhotoApiManager {

    private object Holder {
        val INSTANCE = PhotoApiManager()
    }

    companion object {
        val SERVICE_LOAD_PHOTO_LIST = "service_load_photo_list"
        val SERVICE_LOAD_PHOTO_LIST_AFTER_ID = "service_load_photo_list_after_id"
        val SERVICE_LOAD_PHOTO_LIST_BEFORE_ID = "service_load_photo_list_before_id"
        val instance: PhotoApiManager by lazy { Holder.INSTANCE }
    }


    fun getPhotoList(): LiveData<DefaultResponse<PhotoResponse>> {
        return NextworkLiveDataConverter.convert(
                PhotoApiService.newInstance
                        .createApi()
                        .loadPhotoList()
                        .onErrorResumeNext(NextworkLogError(SERVICE_LOAD_PHOTO_LIST))
                        .compose(ConvertToDefaultResponse<Response<PhotoResponse>, DefaultResponse<PhotoResponse>>()))
    }

    fun getPhotoListAfterId(id: Int): LiveData<DefaultResponse<PhotoResponse>> {
        return NextworkLiveDataConverter.convert(
                PhotoApiService.newInstance
                        .createApi()
                        .loadPhotoListAfterId(id)
                        .onErrorResumeNext(NextworkLogError(SERVICE_LOAD_PHOTO_LIST_AFTER_ID))
                        .compose(ConvertToDefaultResponse<Response<PhotoResponse>, DefaultResponse<PhotoResponse>>()))
    }

    fun getPhotoListBeforeId(id: Int): LiveData<DefaultResponse<PhotoResponse>> {
        return NextworkLiveDataConverter.convert(
                PhotoApiService.newInstance
                        .createApi()
                        .loadPhotoListBeforeId(id)
                        .onErrorResumeNext(NextworkLogError(SERVICE_LOAD_PHOTO_LIST_BEFORE_ID))
                        .compose(ConvertToDefaultResponse<Response<PhotoResponse>, DefaultResponse<PhotoResponse>>()))
    }

}
