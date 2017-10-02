package com.nextzy.nextwork.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

public final class NextworkInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        return chain.proceed(getGenericRequest(chain.request().newBuilder()).build());
    }

    private Request.Builder getGenericRequest(Request.Builder builder) {
        return builder;
    }
}
