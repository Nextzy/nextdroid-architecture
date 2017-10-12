package com.nextzy.nextwork.engine.model;

import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;

import retrofit2.Response;

/**
 * Common class used by API responses.
 *
 * @param <T>
 */
public class NextworkResponse<T> {
    private static final String TAG = NextworkResponse.class.getSimpleName();
    public final int code;
    @Nullable
    public final T body;
    @Nullable
    public final String errorMessage;

    public NextworkResponse( int code, @Nullable T body, @Nullable String errorMessage) {
        this.code = code;
        this.body = body;
        this.errorMessage = errorMessage;
    }

    public NextworkResponse( Throwable error) {
        code = 500;
        body = null;
        errorMessage = error.getMessage();
    }

    public NextworkResponse( Response<T> response) {
        code = response.code();
        if (response.isSuccessful()) {
            body = response.body();
            errorMessage = null;
        } else {
            String message = null;
            if (response.errorBody() != null) {
                try {
                    message = response.errorBody().string();
                } catch (IOException ignored) {
                    Log.e(TAG, "error while parsing response");
                }
            }
            if (message == null || message.trim().length() == 0) {
                message = response.message();
            }
            errorMessage = message;
            body = null;
        }
    }

    public boolean isSuccessful() {
        return code >= 200 && code < 300;
    }
}
