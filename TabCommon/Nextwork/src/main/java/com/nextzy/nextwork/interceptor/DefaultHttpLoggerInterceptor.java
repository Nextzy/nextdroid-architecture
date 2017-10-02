package com.nextzy.nextwork.interceptor;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by trusttanapruk on 8/1/2016.
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
