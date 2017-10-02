package com.nextzy.library.base.mvvm.layer2ViewModel.bus.liveData

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer

/**
 * Created by「 The Khaeng 」on 11 Sep 2017 :)
 */

class EventBusLiveData : LiveData<Any>() {

    fun post(obj: Any) {
        postValue(obj)
    }

    fun subscribe(lifecycle: LifecycleOwner,
                  observer: Observer<Any>) {
        observe(lifecycle, observer)
    }

}
