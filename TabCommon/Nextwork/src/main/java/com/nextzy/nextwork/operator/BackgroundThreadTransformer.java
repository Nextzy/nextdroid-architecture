package com.nextzy.nextwork.operator;

import io.reactivex.Single;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

public class BackgroundThreadTransformer<T> implements SingleTransformer<T, T> {
    @Override
    public Single<T> apply(Single<T> upstream) {
        return upstream.subscribeOn(Schedulers.io())
                .observeOn( AndroidSchedulers.mainThread());
    }
}
