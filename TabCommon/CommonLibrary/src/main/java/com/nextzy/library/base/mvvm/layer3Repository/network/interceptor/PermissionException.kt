package com.nextzy.library.base.mvvm.layer3Repository.network.interceptor

import com.nextzy.nextwork.exception.StatusCodeException


/**
* Created by「 The Khaeng 」on 17 Oct 2017 :)
*/
class PermissionException(code: Int)
    : StatusCodeException(code, "for Forbidden requests, when a request may be valid but the user " + "doesn't have permissions to perform the action")
