package com.nextzy.nextwork.operator;


import com.nextzy.nextwork.bus.NextworkReplaySubject;

import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

public class NextworkSingleSubscriber<T> implements SingleObserver<T> {

    public NextworkSingleSubscriber() {
    }

    /**
     * Provides the SingleObserver with the means of cancelling (disposing) the
     * connection (channel) with the Single in both
     * synchronous (from within {@code onSubscribe(Disposable)} itself) and asynchronous manner.
     *
     * @param d the Disposable instance whose {@link Disposable#dispose()} can
     *          be called anytime to cancel the connection
     * @since 2.0
     */
    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onSuccess(T value) {
        NextworkReplaySubject.getInstance().post(value);
    }

    @Override
    public void onError(Throwable error) {
        NextworkReplaySubject.getInstance().post(error);
    }
}
