package com.nextzy.nextwork.exception;

import java.util.List;

/**
 * Created by TheKhaeng on 10/31/2016.
 */

public class ThrowableWithServiceName extends Throwable {


    private List<String> mServiceNameList;

    public ThrowableWithServiceName(List<String> serviceNameList) {
        mServiceNameList = serviceNameList;
    }

    public List<String> getServiceNameList() {
        return mServiceNameList;
    }

    public void setServiceName(List<String> serviceNameList) {
        mServiceNameList = serviceNameList;
    }
}

