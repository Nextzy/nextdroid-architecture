package com.nextzy.tabcustomize.template.mvvm.repository

import android.arch.lifecycle.LiveData
import com.nextzy.nextwork.engine.AppExecutors
import com.nextzy.nextwork.engine.model.NextworkResponse
import com.nextzy.nextwork.engine.model.Resource
import com.nextzy.tabcustomize.base.repository.database.realm.CustomDatabase
import com.nextzy.tabcustomize.template.mvvm.repository.model.CustomModel
import com.nextzy.tabcustomize.template.mvvm.repository.network.CustomApiService
import com.nextzy.tabcustomize.template.mvvm.repository.network.response.CustomResponse

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

    private val service: CustomApiService = CustomApiService.newInstance
    private val database: CustomDatabase = CustomDatabase.instance
    private val appExecutors: AppExecutors = AppExecutors.getInstance()


    fun getTestResponse(id:Int,isForceFetch: Boolean = false): LiveData<Resource<CustomModel>>
            = object : CustomBoundResource<CustomModel, CustomResponse>(appExecutors) {

        override
        fun saveCallResult(item: CustomModel) {
            database.saveCustomModel(item).subscribe()
        }

        override
        fun shouldFetch(data: CustomModel?): Boolean {
            return isForceFetch
                    || data?.shouldFetch() == true
        }

        override
        fun loadFromDb(): LiveData<CustomModel> {
            return database.loadCustomModelAsLiveData(id)
        }

        override
        fun createCall(): LiveData<NextworkResponse<CustomResponse>> {
            return service
                    .createApi()
                    .getTestResponse()
        }
    }.asLiveData()

}
