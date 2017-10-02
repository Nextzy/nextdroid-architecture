package com.nextzy.library.base.mvvm.layer3Repository.network.interceptor;


import th.co.thekhaeng.waterlibrary.java.base.nextwork.exception.StatusCodeException;

/**
 * Created by TheKhaeng on 9/16/2016.
 */
public class PermissionException extends StatusCodeException{
    public PermissionException(int code) {
        super(code, "for Forbidden requests, when a request may be valid but the user " +
                "doesn't have permissions to perform the action");
    }
}
