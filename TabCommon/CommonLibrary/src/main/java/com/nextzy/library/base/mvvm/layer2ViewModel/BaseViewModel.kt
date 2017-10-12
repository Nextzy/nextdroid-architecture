package com.nextzy.library.base.mvvm.layer2ViewModel


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity

import com.nextzy.library.base.mvvm.layer2ViewModel.bus.rx.EventBusViewModel


/**
 * Created by「 The Khaeng 」on 23 Aug 2017 :)
 */

abstract class BaseViewModel : ViewModel() {
    companion object {
        lateinit var busViewModel: EventBusViewModel
            private set
    }

    init {
        busViewModel = EventBusViewModel()
    }


    fun postEvent(obj: Any) {
        busViewModel.post(obj)
    }

    fun subscribeBus(activity: FragmentActivity,
                     observer: Observer<Any>) {
        busViewModel.newSubscribe(activity, observer)

    }

    fun subscribeBus(fragment: Fragment,
                     observer: Observer<Any>) {
        busViewModel.subscribe(fragment, observer)
    }

}
