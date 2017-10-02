package com.nextzy.library.base.mvvm.layer3Repository.network.operator;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import th.co.thekhaeng.waterlibrary.java.base.nextwork.exception.StatusCodeException;
import th.co.thekhaeng.waterlibrary.java.base.nextwork.operator.NextworkErrorJsonHandling;
import th.co.thekhaeng.waterlibrary.java.base.nextwork.reponse.NextworkResponse;
import th.co.thekhaeng.waterlibrary.java.base.structure.mvvm.layer3Repository.network.interceptor.PermissionException;
import th.co.thekhaeng.waterlibrary.java.base.structure.mvvm.layer3Repository.network.interceptor.UnAuthorizedException;


/**
 * Created by thekhaeng on 5/10/2017 AD.
 */

public class ErrorJsonHandling<T extends NextworkResponse> extends NextworkErrorJsonHandling<T>{

    public ErrorJsonHandling(Class<T> typeOfResultClass, String serviceName) {
        super(typeOfResultClass, serviceName);
    }

    @Override
    protected Single<T> checkJsonResponse(T result) {
        return Single.just(result);
    }

    @Override
    protected List<StatusCodeException> getStatusCodeException() {
        List<StatusCodeException> map = new ArrayList<>();
        map.add(new UnAuthorizedException(401));
        map.add(new PermissionException(403));
        return map;
    }
}
