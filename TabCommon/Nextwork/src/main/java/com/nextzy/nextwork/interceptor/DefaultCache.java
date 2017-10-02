package com.nextzy.nextwork.interceptor;

import java.io.File;

import okhttp3.Cache;

/**
 * Created by thekhaeng on 5/10/2017 AD.
 */

public final class DefaultCache{

    private static final int TEN_MB = 10 * 1024 * 1024;

    /**
     * Instantiates a new Default cache.
     *
     * @param cacheFile the cache file of Application.getCacheDir()
     */
    public static Cache getCache(File cacheFile){
        return new Cache(new File(cacheFile, "http-cache"), TEN_MB);
    }
}
