package com.nextzy.tabcustomize.base.repository.network

import com.nextzy.nextwork.engine.AppExecutors
import com.nextzy.nextwork.engine.NextworkBoundResource
import com.nextzy.nextwork.engine.NextworkResourceCreator

abstract class DefaultNetworkBoundResource<ResultType, RequestType>
    : NextworkBoundResource<ResultType, RequestType, DefaultResponse<RequestType>, DefaultResource<ResultType>>(
        AppExecutors.getInstance(),
        creator()) {


}

fun <ResultType> creator(): NextworkResourceCreator<ResultType, DefaultResource<ResultType>>
        = object : NextworkResourceCreator<ResultType, DefaultResource<ResultType>> {
    override
    fun loading(data: ResultType): DefaultResource<ResultType> {
        return DefaultResource.loading(data)
    }

    override
    fun success(newData: ResultType): DefaultResource<ResultType> {
        return DefaultResource.success(newData)
    }

    override
    fun error(error: Throwable?, newData: ResultType): DefaultResource<ResultType> {
        return DefaultResource.error(error, newData)
    }
}









