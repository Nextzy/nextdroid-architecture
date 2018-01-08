package com.nextzy.library.base.delegate

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Lifecycle.Event.ON_DESTROY
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.nextzy.library.R
import timber.log.Timber
import java.lang.ref.WeakReference


class FragmentHelperDelegate(
        fragmentActivity: FragmentActivity)
    : FragmentHelper, LifecycleObserver {
    private val lifecycle: Lifecycle = fragmentActivity.lifecycle
    private var containerLayoutRes = -1
    private var fragmentActivity: WeakReference<FragmentActivity> = WeakReference(fragmentActivity)
    private var isCanCommit = false

    private val view: FragmentActivity
        get() = fragmentActivity.get()!!


    private val visibleFragment: Fragment?
        get() {
            val fragmentManager = view.supportFragmentManager
            val fragments = fragmentManager.fragments
            fragments?.filter { it?.isVisible == true }?.forEach { return it }
            return null
        }


    override
    fun getCurrentFragment(): Fragment? = visibleFragment

    init {
        lifecycle.addObserver(this)
    }

    fun setCanCommit(canCommit: Boolean) {
        isCanCommit = canCommit
    }

    fun isCanCommit(fragment: Fragment): Boolean {
        return !isSameFragment(fragment) && isCanCommit
    }

    fun logAvailableFragment() {
        Timber.d(javaClass.simpleName, "fragment size: " + view.supportFragmentManager.fragments.size)
        Timber.d(javaClass.simpleName, "START Fragment Lists")
        view.supportFragmentManager.fragments
                .filter { it != null && it.tag != null }
                .forEach { Timber.d(javaClass.simpleName, "Tag: " + it.tag) }
        Timber.d(javaClass.simpleName, "END Fragment Lists")
    }

    override
    fun bindFragmentContainer(containerLayoutRes: Int) {
        this.containerLayoutRes = containerLayoutRes
    }

    override
    fun initFragment(fragment: Fragment) {
        throwNullIfNotBind()
        createTransaction()
                .setContainerLayout(containerLayoutRes)
                .setFinish(true)
                .add(view, fragment)
    }

    override
    fun contain(tag: String): Boolean {
        if (view.supportFragmentManager.fragments == null || view.supportFragmentManager.fragments.size == 0) {
            return false
        }
        return view.supportFragmentManager.fragments.any { it?.tag?.contains(tag) == true }
    }

    override
    fun clearBackStackFragmentTo(index: Int) {
        if (view.supportFragmentManager.backStackEntryCount > 0) {
            val count = view.supportFragmentManager.backStackEntryCount
            for (i in 0 until count) {
                view.supportFragmentManager.popBackStackImmediate()
                if (i == count - index) break
            }
        }
    }

    override
    fun clearAllBackStacksFragment() {
        if (view.supportFragmentManager.backStackEntryCount > 0) {
            clearBackStackFragmentTo(0)
        }

    }

    override
    fun backPressedFragment() {
        view.supportFragmentManager.popBackStack()
    }

    override
    fun backPressedToFragment(tag: String) {
        if (contain(tag)) {
            view.supportFragmentManager.popBackStackImmediate(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    override
    fun openFragmentSlideInRight(fragment: Fragment) {
        if (!isCanCommit(fragment)) return
        createTransaction()
                .setContainerLayout(containerLayoutRes)
                .setAnim(R.anim.slide_left_in, R.anim.slide_left_out)
                .setPopAnim(R.anim.slide_right_in, R.anim.slide_right_out)
                .open(view, fragment)
    }

    override
    fun openFragmentSlideInLeft(fragment: Fragment) {
        if (!isCanCommit(fragment)) return
        createTransaction()
                .setContainerLayout(containerLayoutRes)
                .setAnim(R.anim.slide_right_in, R.anim.slide_right_out)
                .setPopAnim(R.anim.slide_left_in, R.anim.slide_left_out)
                .open(view, fragment)
    }

    override
    fun openFragment(fragment: Fragment) {
        if (!isCanCommit(fragment)) return
        createTransaction()
                .setContainerLayout(containerLayoutRes)
                .open(view, fragment)
    }

    @OnLifecycleEvent(ON_DESTROY)
    fun destroyView() {
        lifecycle.removeObserver(this)
    }

    fun handleBackButton() {
        if (view.supportFragmentManager.backStackEntryCount > 1) {
            view.supportFragmentManager.popBackStack()
        } else {
            view.finish()
        }
    }

    fun createTransaction(): OpenFragmentTransaction {
        return OpenFragmentTransaction()
    }
    /* ============================ Private method ============================================== */

    private fun isSameFragment(fragment: Fragment): Boolean {
        val currentFragment = getCurrentFragment()
        return null != currentFragment && currentFragment.javaClass == fragment.javaClass
    }

    private fun throwNullIfNotBind() {
        if (containerLayoutRes == -1)
            throw NullPointerException("bindFragmentContainer() first.")
    }

}
