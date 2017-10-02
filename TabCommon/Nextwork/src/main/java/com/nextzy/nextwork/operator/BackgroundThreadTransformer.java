package com.nextzy.nextwork.operator;

import io.reactivex.Single;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Akexorcist on 3/26/2017 AD.
 */

public class BackgroundThreadTransformer<T> implements SingleTransformer<T, T> {
    @Override
    public Single<T> apply(Single<T> upstream) {
        return upstream.subscribeOn(Schedulers.io())
                .observeOn( AndroidSchedulers.mainThread());
    }
}
