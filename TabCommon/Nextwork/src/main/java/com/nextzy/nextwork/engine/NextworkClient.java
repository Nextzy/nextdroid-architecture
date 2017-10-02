package com.nextzy.nextwork.engine;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * Created by「 The Khaeng 」on 29 Sep 2017 :)
 */

public abstract class NextworkClient{
    private OkHttpClient okHttpClient;

    public NextworkClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        configBuilder( builder );

        List<Interceptor> interceptorList = setupInterceptorList();
        if( interceptorList != null && !interceptorList.isEmpty() ){
            for( Interceptor interceptor : interceptorList ){
                builder.addInterceptor( interceptor );
            }
        }

        okHttpClient = builder
                .readTimeout( getDefaultTimeout(), TimeUnit.MILLISECONDS )
                .writeTimeout( getDefaultTimeout(), TimeUnit.MILLISECONDS )
                .connectTimeout( getDefaultTimeout(), TimeUnit.MILLISECONDS )
                .build();
    }


    protected long getDefaultTimeout(){
        return 60000;
    }

    public OkHttpClient getOkHttpClient(){
        return okHttpClient;
    }

    public void configBuilder( OkHttpClient.Builder builder ){
    }

    public List<Interceptor> setupInterceptorList(){
        return null;
    }

}
