package com.nextzy.nextwork.operator;


import io.reactivex.Single;
import io.reactivex.functions.Function;
import th.co.thekhaeng.waterlibrary.java.base.nextwork.reponse.NextworkResponse;


/**
 * Created by thekhaeng on 5/10/2017 AD.
 */

public abstract class NextworkResponseResultCheck<T extends NextworkResponse, E extends Throwable>
        implements Function<T, Single<T>> {

    protected abstract E getThrowable(String message);
}
