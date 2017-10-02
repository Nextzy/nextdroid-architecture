package com.nextzy.nextwork.bus;

import io.reactivex.functions.Consumer;


/**
 * Created by「 The Khaeng 」on 18 Sep 2017 :)
 */

public class NextworkBus{

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
        objectName = getName( object );

        NextworkReplaySubject.getInstance()
                .observe()
                .subscribe(
                        newConsumeSuccess(),
                        newConsumeException() );
    }


    public static void unregister(){
        objectName = null;
        subscriberCallBack = null;
    }

    public static void clear(){
        unregister();
        NextworkReplaySubject.getInstance().clearCache();
    }

    private static String getName( Object object ){
        if( object == null ) return "";
        return object.getClass().getSimpleName();
    }

    private static Consumer<Throwable> newConsumeException(){
        return new Consumer<Throwable>(){
            @Override
            public void accept( Throwable throwable ) throws Exception{
                if( subscriberCallBack != null ){
                    subscriberCallBack.onResponseError( throwable );
                }
            }
        };
    }

    private static Consumer<Object> newConsumeSuccess(){
        return new Consumer<Object>(){
            @Override
            public void accept( Object o ) throws Exception{
                if( o instanceof Throwable ){
                    if( subscriberCallBack != null ){
                        subscriberCallBack.onResponseError( (Throwable) o );
                    }
                }else{
                    if( subscriberCallBack != null ){
                        subscriberCallBack.onResponseSuccess( o );
                    }
                }

            }
        };
    }

}
