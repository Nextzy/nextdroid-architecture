package com.nextzy.nextwork.interceptor;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

public final class DefaultHttpLoggerInterceptor {
    public static HttpLoggingInterceptor getInterceptor(boolean isShowLog) {
        if (isShowLog) {
            return new HttpLoggingInterceptor(new NextworkHttpLogger()).setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE);
        }
    }
}
