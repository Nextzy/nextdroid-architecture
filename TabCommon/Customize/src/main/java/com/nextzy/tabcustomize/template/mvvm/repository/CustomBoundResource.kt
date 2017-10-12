@file:Suppress("UNCHECKED_CAST")

package com.nextzy.tabcustomize.template.mvvm.repository

import com.nextzy.nextwork.engine.AppExecutors
import com.nextzy.nextwork.engine.NextworkBoundResource
import com.nextzy.tabcustomize.template.mvvm.repository.model.CustomModel
import com.nextzy.tabcustomize.template.mvvm.repository.network.response.CustomResponse

abstract class CustomBoundResource<ResultType: CustomModel, RequestType:CustomResponse>(appExecutors: AppExecutors)
    : NextworkBoundResource<ResultType, RequestType>(appExecutors) {

     override
     fun convertToResultType(requestType: RequestType?): ResultType {
         return CustomModel() as ResultType
     }
 }
