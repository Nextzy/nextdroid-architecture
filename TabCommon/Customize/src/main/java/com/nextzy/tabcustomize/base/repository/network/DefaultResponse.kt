package com.nextzy.tabcustomize.base.repository.network

import com.nextzy.nextwork.engine.model.NextworkResponse

import retrofit2.Response

/**
 * Default class used by API responses.
 *
 * @param <T>
</T> */
class DefaultResponse<T> : NextworkResponse<T> {

    constructor(code: Int, body: T?, error: Throwable?) : super(code, body, error) {}

    constructor(error: Throwable) : super(error) {}

    constructor(response: Response<T>) : super(response) {}
}
