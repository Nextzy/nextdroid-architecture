package com.nextzy.library.base.mvvm.layer3Repository.network.operator


import com.nextzy.library.base.mvvm.layer3Repository.network.interceptor.PermissionException
import com.nextzy.library.base.mvvm.layer3Repository.network.interceptor.UnAuthorizedException
import com.nextzy.nextwork.exception.StatusCodeException
import com.nextzy.nextwork.operator.NextworkErrorJsonHandling
import com.nextzy.nextwork.reponse.NextworkResponse
import io.reactivex.Single
import java.util.*


/**
 * Created by「 The Khaeng 」on 03 Oct 2017 :)
 */

class ErrorJsonHandling<T : NextworkResponse>(typeOfResultClass: Class<T>, serviceName: String)
    : NextworkErrorJsonHandling<T>(typeOfResultClass, serviceName) {

    override
    fun checkJsonResponse(result: T): Single<T> = Single.just(result)

    override
    fun getStatusCodeException(): List<StatusCodeException> {
        val map = ArrayList<StatusCodeException>()
        map.add(UnAuthorizedException(401))
        map.add(PermissionException(403))
        return map
    }
}
