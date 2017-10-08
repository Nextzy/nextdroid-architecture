package com.nextzy.library.base.mvvm.layer2ViewModel.bus.rx

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer

import rx.Subscriber


/**
 * Created by「 The Khaeng 」on 18 Sep 2017 :)
 */

class EventBusViewModel : LifecycleObserver {
    private val replaySubject: EventBusReplaySubject = EventBusReplaySubject()
    private val subscriber: Subscriber<*>? = null

    private var observer: Observer<Any>? = null
    private var currentState: Lifecycle.State? = null

    fun post(obj: Any) {
        replaySubject.post(obj)
    }

    fun newSubscribe(lifecycle: LifecycleOwner,
                     observer: Observer<Any>) {
        replaySubject.clearCache()
        subscribe(lifecycle, observer)
    }

    fun subscribe(lifecycle: LifecycleOwner,
                  observer: Observer<Any>) {
        this.observer = observer
        this.currentState = lifecycle.lifecycle.currentState

        this.replaySubject.observe().subscribe(
                { o ->
                    observer.onChanged(o)
                })
        { throwable ->
            observer.onChanged(throwable)
        }
    }

}
