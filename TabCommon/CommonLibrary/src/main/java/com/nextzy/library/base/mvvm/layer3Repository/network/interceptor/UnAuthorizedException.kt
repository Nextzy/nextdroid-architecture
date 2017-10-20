package com.nextzy.library.base.mvvm.layer3Repository.network.interceptor


import com.nextzy.nextwork.exception.StatusCodeException

/**
 * Created by「 The Khaeng 」on 03 Oct 2017 :)
 */
class UnAuthorizedException(code: Int)
    : StatusCodeException(code, "for Unauthorized requests, when a request requires authentication " + "but it isn't provided")
