package com.nextzy.library.base.mvvm.layer3Repository.network.interceptor

import com.nextzy.nextwork.exception.StatusCodeException


/**
 * Created by TheKhaeng on 9/16/2016.
 */
class PermissionException(code: Int) : StatusCodeException(code, "for Forbidden requests, when a request may be valid but the user " + "doesn't have permissions to perform the action")
