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
public class NextworkResponse<T>{
    private static final String TAG = NextworkResponse.class.getSimpleName();
    public final int code;

    @Nullable
    public final T body;

    @Nullable
    public final Throwable error;

    public NextworkResponse( int code, @Nullable T body, @Nullable Throwable error ){
        this.code = code;
        this.body = body;
        this.error = error;
    }

    public NextworkResponse( Throwable error ){
        this.code = 500;
        this.body = null;
        this.error = error;
    }

    public NextworkResponse( Response<T> response ){
        code = response.code();
        if( response.isSuccessful() ){
            body = response.body();
            error = null;
        }else{
            String message = null;
            if( response.errorBody() != null ){
                try{
                    message = response.errorBody().string();
                }catch( IOException ignored ){
                    Log.e( TAG, "error while parsing response" );
                }
            }
            if( message == null || message.trim().length() == 0 ){
                message = response.message();
            }
            error = new IllegalArgumentException(message);
            body = null;
        }
    }

    public boolean isSuccessful(){
        return code >= 200 && code < 300;
    }
}
