package com.nextzy.library.extension

import android.arch.core.util.Function
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import com.nextzy.nextwork.engine.AbsentLiveData

/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

fun <T> LiveData<Boolean>.fetchSwitchMap(func: Function<Boolean, LiveData<T>>): LiveData<T> {
    return Transformations.switchMap<Boolean, T>(
            this,
            { forceFetch ->
                if (forceFetch == null) {
                    AbsentLiveData.create<T>()
                } else {
                    func.apply(forceFetch)
                }
            })
}
