package com.nextzy.library.base.mvvm.layer3Repository.network.base;

import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * Created by「 The Khaeng 」on 29 Sep 2017 :)
 */

public class DefaultClient extends BaseClient{
    private static DefaultClient instance;

    public static DefaultClient getInstance() {
        if (instance == null) {
            instance = new DefaultClient();
        }
        return instance;
    }

    @Override
    public void configBuilder( OkHttpClient.Builder builder ){
        super.configBuilder( builder );
    }

    @Override
    public List<Interceptor> setupInterceptorList(){
        return super.setupInterceptorList();
    }
}
