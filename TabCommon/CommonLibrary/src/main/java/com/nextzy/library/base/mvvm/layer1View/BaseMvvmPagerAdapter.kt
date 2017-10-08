package com.nextzy.library.base.mvvm.layer1View

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

import com.nextzy.library.base.mvvm.exception.ViewModelNotNullException
import com.nextzy.library.base.mvvm.exception.ViewModelNotSetupException
import com.nextzy.library.base.mvvm.layer2ViewModel.BaseViewModel

import java.lang.ref.WeakReference

/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

abstract class BaseMvvmPagerAdapter<VM : BaseViewModel> : FragmentStatePagerAdapter {

    private var fm: WeakReference<FragmentManager>
    private var activity: WeakReference<FragmentActivity>? = null
    private var fragment: WeakReference<Fragment>? = null
    var context: Context? = null
        private set

    val fragmentManager: FragmentManager
        get() = fm.get()!!

    private val viewModelShared: VM? = null

    val viewModel: VM?
        get() {
            if (setupViewModel() == null) throw ViewModelNotSetupException()
            if (fragment != null) {
                return ViewModelProviders.of(fragment?.get()!!)
                        .get(setupViewModel())
            } else if (activity != null) {
                return ViewModelProviders.of(activity?.get()!!)
                        .get(setupViewModel())
            }
            return null
        }

    constructor(activity: FragmentActivity) : super(activity.supportFragmentManager) {
        this.context = activity
        this.fm = WeakReference(activity.supportFragmentManager)
        this.activity = WeakReference(activity)
    }

    constructor(fragment: Fragment) : super(fragment.childFragmentManager) {
        this.context = fragment.context
        this.fm = WeakReference(fragment.childFragmentManager)
        this.fragment = WeakReference(fragment)
    }

    abstract fun setupViewModel(): Class<VM>?


    fun getViewModelShared(): VM? {
        if (setupViewModel() == null) throw ViewModelNotSetupException()
        if (fragment != null) {
            return if (fragment?.get()?.activity != null) {
                ViewModelProviders.of(fragment?.get()?.activity!!)
                        .get(setupViewModel())
            } else null
        } else if (activity != null) {
            return ViewModelProviders.of(activity?.get()!!)
                    .get(setupViewModel())
        }
        return null
    }

    fun getViewModel(viewModelClass: Class<VM>): VM? {
        if (setupViewModel() == null) throw ViewModelNotNullException()
        if (fragment != null) {
            return ViewModelProviders.of(fragment?.get()!!)
                    .get(viewModelClass)
        } else if (activity != null) {
            return ViewModelProviders.of(activity?.get()!!)
                    .get(viewModelClass)
        }
        return null
    }


}
