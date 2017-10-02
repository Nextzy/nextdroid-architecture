package com.nextzy.nextwork.interceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

public final class DefaultCacheInterceptor implements Interceptor {
    private static final String CACHE_CONTROL = "Cache-Control";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        // re-write response header to force use of cache
        CacheControl cacheControl = new CacheControl.Builder()
                .maxAge(2, TimeUnit.MINUTES)
                .build();

        return response.newBuilder()
                .header(CACHE_CONTROL, cacheControl.toString())
                .build();
    }
}
