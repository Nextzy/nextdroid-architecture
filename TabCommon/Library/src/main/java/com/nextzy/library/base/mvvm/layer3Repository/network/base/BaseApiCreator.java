package com.nextzy.library.base.mvvm.layer3Repository.network.base;


import okhttp3.OkHttpClient;
import th.co.thekhaeng.waterlibrary.java.base.nextwork.engine.NextworkApiCreator;

/**
 * Created by thekhaeng on 5/10/2017 AD.
 */

public abstract class BaseApiCreator<T> extends NextworkApiCreator<T>{
    public static final String BASE_URL = "";

    private static BaseApiCreator instance;

    public BaseApiCreator(Class<T> apiClass) {
        super(apiClass);
    }

    @Override
    public String getBaseUrl() {
        return BASE_URL;
    }

    @Override
    public OkHttpClient getClient(){
        return DefaultClient.getInstance().getOkHttpClient();
    }
}
