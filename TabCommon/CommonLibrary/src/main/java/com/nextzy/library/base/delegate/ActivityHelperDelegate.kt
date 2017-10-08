package com.nextzy.library.base.delegate

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Lifecycle.Event.ON_DESTROY
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import java.lang.ref.WeakReference

/**
 * Created by「 The Khaeng 」on 31 Aug 2017 :)
 */

class ActivityHelperDelegate
    : LifecycleObserver,
      ActivityHelper {

    private var fragment: WeakReference<Fragment>? = null
    private var lifecycle: Lifecycle? = null
    private var activity: WeakReference<Activity>? = null

    constructor(activity: FragmentActivity) {
        this.activity = WeakReference(activity)
        lifecycle = activity.lifecycle
        lifecycle?.addObserver(this)
    }

    constructor(fragment: Fragment) {
        this.fragment = WeakReference(fragment)
        lifecycle = fragment.lifecycle
        lifecycle?.addObserver(this)
    }

    fun createTransaction(): OpenActivityTransaction {
        return OpenActivityTransaction()
    }

    override
    fun openActivity(targetClass: Class<*>,
                     request: Int,
                     data: Bundle?) {
        val openActivityTransaction = createTransaction()
                .setRequestCode(request)
                .setRequestCode(request)
        open(openActivityTransaction, targetClass)
    }



    override
    fun openActivityWithFinish(targetClass: Class<*>, data: Bundle?) {
        val openActivityTransaction = createTransaction()
                .setBundle(data)
                .setFinish(true)
        open(openActivityTransaction, targetClass)
    }


    override
    fun openActivityWithAllFinish(targetClass: Class<*>, data: Bundle?) {
        val openActivityTransaction = createTransaction()
                .setBundle(data)
                .setFinishAllPrevious(true)
        open(openActivityTransaction, targetClass)
    }


    @OnLifecycleEvent(ON_DESTROY)
    fun destroyView() {
        lifecycle?.removeObserver(this)
        activity = null
        fragment = null
    }

    /* =========================== Private method ============================================= */
    private fun open(openActivityTransaction: OpenActivityTransaction, targetClass: Class<*>) {
        if (getActivity() != null) {
            openActivityTransaction.open(getActivity(), targetClass)
        } else if (getFragment() != null) {
            openActivityTransaction.open(getFragment(), targetClass)
        }
    }


    private fun getActivity(): Activity? {
        return activity?.get()
    }

    private fun getFragment(): Fragment? {
        return fragment?.get()
    }
}
