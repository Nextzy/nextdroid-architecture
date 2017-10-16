package com.nextzy.nextwork.operator;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

/**
 * Created by「 The Khaeng 」on 16 Oct 2017 :)
 */

public class NextworkLogError<T> implements Function<Throwable, SingleSource<T>>{
    private final static String TAG = NextworkLogError.class.getSimpleName();

    private String serviceName;

    public NextworkLogError( String serviceName ){
        this.serviceName = serviceName;
    }

    @Override
    public SingleSource<T> apply( Throwable throwable ) throws Exception{
        NLog.e( TAG, serviceName, throwable );
        return Single.error( throwable );
    }
}
