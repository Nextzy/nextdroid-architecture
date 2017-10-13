package com.nextzy.nextwork.engine;

import android.arch.lifecycle.LiveData;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by「 The Khaeng 」on 12 Oct 2017 :)
 */

public class NextworkLiveDataConverter{

    public static <T> LiveData<T> convert( final Single<T> single ){
        return new LiveData<T>(){
            Disposable disposable;

            @Override
            protected void onActive(){
                super.onActive();
                disposable = single
                        .subscribeOn( Schedulers.io() )
                        .subscribe( this::postValue );
            }

            @Override
            protected void onInactive(){
                super.onInactive();
                if( disposable != null ) disposable.dispose();
            }
        };


    }
}
