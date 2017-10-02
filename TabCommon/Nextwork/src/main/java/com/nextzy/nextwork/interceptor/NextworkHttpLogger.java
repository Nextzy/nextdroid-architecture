package com.nextzy.nextwork.interceptor;

import android.util.Log;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */
public final class NextworkHttpLogger implements HttpLoggingInterceptor.Logger {
    private static final String TAG = NextworkHttpLogger.class.getSimpleName();

    @Override
    public void log(String message) {
        if (!message.startsWith("{")) {
            Log.d(TAG, message);
            return;
        }
        try {
            String prettyPrintJson = new GsonBuilder().setPrettyPrinting().create().toJson(new JsonParser().parse(message));
            Log.d(TAG, prettyPrintJson);
        } catch (JsonSyntaxException m) {
            m.printStackTrace();
            Log.d(TAG, message);
        }
    }
}
