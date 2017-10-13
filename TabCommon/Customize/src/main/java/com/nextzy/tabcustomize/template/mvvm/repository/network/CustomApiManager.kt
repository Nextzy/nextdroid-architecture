package com.nextzy.tabcustomize.template.mvvm.repository.network

import android.arch.lifecycle.LiveData
import com.nextzy.nextwork.engine.NextworkLiveDataConverter
import com.nextzy.tabcustomize.base.repository.network.ConvertToDefaultResponse
import com.nextzy.tabcustomize.base.repository.network.DefaultResponse
import com.nextzy.tabcustomize.template.mvvm.repository.network.api.CustomApiService
import com.nextzy.tabcustomize.template.mvvm.repository.network.model.reponse.CustomResponse
import retrofit2.Response

/**
 * Created by「 The Khaeng 」on 12 Oct 2017 :)
 */

class CustomApiManager {

    private object Holder {
        val INSTANCE = CustomApiManager()
    }

    companion object {
        val SERVICE_TEST = "service_test"
        val instance: CustomApiManager by lazy { Holder.INSTANCE }
    }


    fun getTestLiveData(): LiveData<DefaultResponse<CustomResponse>> {
        return NextworkLiveDataConverter.convert(
                CustomApiService.newInstance
                        .createApi()
                        .getTestResponse()
                        .compose(ConvertToDefaultResponse<Response<CustomResponse>, DefaultResponse<CustomResponse>>())
//                        .compose(ErrorJsonHandling(NextworkResponse::class.java, SERVICE_TEST))
                                                );
    }

}
