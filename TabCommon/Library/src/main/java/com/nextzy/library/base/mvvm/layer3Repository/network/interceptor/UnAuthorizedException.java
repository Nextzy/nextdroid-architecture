package com.nextzy.library.base.mvvm.layer3Repository.network.interceptor;


import th.co.thekhaeng.waterlibrary.java.base.nextwork.exception.StatusCodeException;

/**
 * Created by TheKhaeng on 9/16/2016.
 */
public class UnAuthorizedException extends StatusCodeException{
    public UnAuthorizedException(int code) {
        super(code, "for Unauthorized requests, when a request requires authentication " +
                "but it isn't provided");
    }
}
