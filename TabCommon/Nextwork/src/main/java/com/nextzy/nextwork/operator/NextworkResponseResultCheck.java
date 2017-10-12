package com.nextzy.nextwork.operator;


import io.reactivex.Single;
import io.reactivex.functions.Function;


/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

public abstract class NextworkResponseResultCheck<T, E extends Throwable>
        implements Function<T, Single<T>> {

    protected abstract E getThrowable(String message);
}
