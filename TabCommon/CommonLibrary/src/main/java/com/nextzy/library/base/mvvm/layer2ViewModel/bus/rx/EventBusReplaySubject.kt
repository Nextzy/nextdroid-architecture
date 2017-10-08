package com.nextzy.library.base.mvvm.layer2ViewModel.bus.rx


import io.reactivex.Observable
import io.reactivex.subjects.ReplaySubject
import io.reactivex.subjects.Subject

/**
 * Created by「 The Khaeng 」on 18 Sep 2017 :)
 */

class EventBusReplaySubject {

    //SerializedSubject is very important - we want to be able to post/subscribe items
    //on different threads - http://reactivex.io/RxJava/javadoc/rx/subjects/SerializedSubject.html
    private var rxBus: Subject<Any> = ReplaySubject.createWithSize(DEFAULT_CACHE_SIZE)

    /**
     * General method for publishing of events
     *
     * @param obj
     */
    fun post(obj: Any) {
        rxBus.onNext(obj)
    }

    fun clearCache() {
        rxBus.onComplete()
        rxBus = ReplaySubject.createWithSize(DEFAULT_CACHE_SIZE)
    }


    /**
     * General method for observing on all events
     *
     * @return
     */
    fun observe(): Observable<Any> {
        return rxBus
    }

    /**
     * Method to observe on certain Event class
     *
     * @param eventType
     * @param <T>
     * @return
    </T> */
    fun <T : Any> observe(eventType: Class<T>): Observable<T> {
        return rxBus.filter({ eventType.isInstance(it) }).cast(eventType)
    }


    /**
     * @param eventType
     * @param <T>
     * @return Return true if subject has any event of certain type, otherwise return false
    </T> */
    fun <T : Any> hasEventsOfType(eventType: Class<T>): Boolean {
        val events = (rxBus as ReplaySubject<*>).values

        val count = events.count { eventType.isInstance(it) }

        return count > 0
    }

    companion object {
        private val DEFAULT_CACHE_SIZE = 5
    }

}
