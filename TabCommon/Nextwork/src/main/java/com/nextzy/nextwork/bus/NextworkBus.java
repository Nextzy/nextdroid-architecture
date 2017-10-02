package com.nextzy.nextwork.bus;

import android.support.annotation.NonNull;

import rx.Subscriber;


/**
 * Created by「 The Khaeng 」on 18 Sep 2017 :)
 */

public class NextworkBus{
    private static Subscriber subscriber;

    private static String objectName;
    private static NextworkSubscriber subscriberCallBack;

    public interface NextworkSubscriber{
        void onResponseSuccess( Object response );

        void onResponseError( Throwable error );
    }

    @SuppressWarnings( "unchecked" )
    public static void register( Object object, NextworkSubscriber callback ){
        if( !getName( object ).equals( objectName ) ) clear();

        subscriberCallBack = callback;
        subscriber = newSubscriber();
        objectName = getName( object );

        NextworkReplaySubject.getInstance()
                .observe()
                .subscribe( subscriber );
    }

    public static void unregister(){
        objectName = null;
        subscriberCallBack = null;
        if( subscriber != null && !subscriber.isUnsubscribed() ){
            subscriber.unsubscribe();
            subscriber = null;
        }
    }

    public static void clear(){
        unregister();
        NextworkReplaySubject.getInstance().clearCache();
    }

    private static String getName( Object object ){
        if( object == null ) return "";
        return object.getClass().getSimpleName();
    }

    @NonNull
    private static Subscriber newSubscriber(){
        return new Subscriber(){
            @Override
            public void onCompleted(){
                // do nothing
            }

            @Override
            public void onError( Throwable error ){
                if( subscriberCallBack != null ){
                    subscriberCallBack.onResponseError( error );
                }
            }

            @Override
            public void onNext( Object object ){
                if( object instanceof Throwable ){
                    onError( (Throwable) object );
                }else{
                    if( subscriberCallBack != null ){
                        subscriberCallBack.onResponseSuccess( object );
                    }
                }
            }
        };
    }
}
