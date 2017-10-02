package com.nextzy.nextwork.bus;

import rx.Observable;
import rx.subjects.ReplaySubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by「 The Khaeng 」on 18 Sep 2017 :)
 */

public class NextworkReplaySubject{
    private static final int DEFAULT_CACHE_SIZE = 5;
    private static NextworkReplaySubject mInstance;

    //SerializedSubject is very important - we want to be able to post/subscribe items
    //on different threads - http://reactivex.io/RxJava/javadoc/rx/subjects/SerializedSubject.html
    private Subject<Object, Object> rxBus =
            new SerializedSubject<>( ReplaySubject.createWithSize( DEFAULT_CACHE_SIZE ) );

    private NextworkReplaySubject(){
    }

    static public NextworkReplaySubject getInstance(){
        if( mInstance == null ){
            mInstance = new NextworkReplaySubject();
        }

        return mInstance;
    }

    /**
     * General method for publishing of events
     *
     * @param obj
     */
    public void post( Object obj ){
        rxBus.onNext( obj );
    }

    public void clearCache(){
        rxBus.onCompleted();
        rxBus = new SerializedSubject<>( ReplaySubject.createWithSize( DEFAULT_CACHE_SIZE ) );
    }

    /**
     * General method for observing on all events
     *
     * @return
     */
    public Observable<Object> observe(){
        return rxBus;
    }

    /**
     * Method to observe on certain Event class
     *
     * @param eventType
     * @param <T>
     * @return
     */
    public <T extends Object> Observable<T> observe( final Class<T> eventType ){
        return rxBus.filter( eventType::isInstance ).cast( eventType );
    }


    /**
     * @param eventType
     * @param <T>
     * @return Return true if subject has any event of certain type, otherwise return false
     */
    public <T extends Object> boolean hasEventsOfType( final Class<T> eventType ){
        Object[] events = ( (ReplaySubject) rxBus ).getValues();

        int count = 0;
        for( Object e : events ){
            if( eventType.isInstance( e ) ){
                count++;
            }
        }

        return ( count > 0 );
    }

}
