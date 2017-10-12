package com.nextzy.nextwork.factory;


import android.arch.lifecycle.LiveData;

import com.nextzy.nextwork.engine.model.NextworkResponse;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A Retrofit adapterthat converts the Call into a LiveData of NextworkResponse.
 * @param <R>
 */
public class LiveDataCallAdapter<R> implements CallAdapter<R, LiveData<NextworkResponse<R>>> {
    private final Type responseType;
    public LiveDataCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public LiveData<NextworkResponse<R>> adapt( final Call<R> call) {
        return new LiveData<NextworkResponse<R>>() {
            AtomicBoolean started = new AtomicBoolean(false);
            @Override
            protected void onActive() {
                super.onActive();
                if (started.compareAndSet(false, true)) {
                    call.enqueue(new Callback<R>() {
                        @Override
                        public void onResponse(Call<R> call, Response<R> response) {
                            postValue(new NextworkResponse<>(response));
                        }

                        @Override
                        public void onFailure(Call<R> call, Throwable throwable) {
                            postValue(new NextworkResponse<R>(throwable));
                        }
                    });
                }
            }
        };
    }
}
