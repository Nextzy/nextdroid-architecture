package com.nextzy.tabcustomize.template.mvvm.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.nextzy.tabcustomize.base.repository.database.realm.CustomDatabase
import com.nextzy.tabcustomize.base.repository.network.DefaultNetworkBoundResource
import com.nextzy.tabcustomize.base.repository.network.DefaultResource
import com.nextzy.tabcustomize.base.repository.network.DefaultResponse
import com.nextzy.tabcustomize.template.mvvm.repository.model.CustomModel
import com.nextzy.tabcustomize.template.mvvm.repository.network.CustomApiManager
import com.nextzy.tabcustomize.template.mvvm.repository.network.model.reponse.CustomResponse

/**
 * Created by「 The Khaeng 」on 12 Oct 2017 :)
 */

class CustomRealmRepository private constructor() {

    private object Holder {
        val INSTANCE = CustomRealmRepository()
    }

    companion object {
        val instance: CustomRealmRepository by lazy { Holder.INSTANCE }
    }

    private val serviceManager: CustomApiManager = CustomApiManager.instance
    private val database: CustomDatabase = CustomDatabase.instance

    fun getTestResponse(id: Int, isForceFetch: Boolean = false): LiveData<DefaultResource<CustomModel>>
            = object : DefaultNetworkBoundResource<CustomModel, CustomResponse>() {
        override
        fun saveCallResult(item: CustomModel) {
//            database.saveCustomModel(item).subscribe()
        }

        override
        fun shouldFetch(data: CustomModel?): Boolean {
            return isForceFetch
                    || data?.shouldFetch() == true
        }

        override
        fun loadFromDb(): LiveData<CustomModel> {
            return MutableLiveData()
//            return database.loadCustomModelAsLiveData(id)
        }

        override
        fun createCall(): LiveData<DefaultResponse<CustomResponse>> {
            return serviceManager.getTestLiveData()
        }


        override
        fun convertToResultType(requestType: CustomResponse?): CustomModel {
            return CustomModel()
        }

    }.asLiveData()

}
