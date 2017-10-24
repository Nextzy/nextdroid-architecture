package com.nextzy.library.base.mvvm.layer1View

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.nextzy.library.base.delegate.*
import com.nextzy.library.base.mvvm.exception.NotSetLayoutException
import com.nextzy.library.base.utils.android.ScreenOrientationHelper
import timber.log.Timber


/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

abstract class BaseActivity
    : AppCompatActivity(),
      ActivityHelper,
      FragmentHelper,
      ScreenOrientationHelper.ScreenOrientationChangeListener {

    private val helper = ScreenOrientationHelper()
    private lateinit var activityOpener: ActivityHelperDelegate
    private lateinit var fragmentDelegate: FragmentHelperDelegate



    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        helper.setActivity(this)
        helper.onCreate(savedInstanceState)
        helper.setScreenOrientationChangeListener(this)
        val layoutResId = setupLayoutView()
        if (setupLayoutView() == 0) throw NotSetLayoutException()
        activityOpener = ActivityHelperDelegate(this)
        fragmentDelegate = FragmentHelperDelegate(this)
        Timber.d("setContentView: ")
        setContentView(layoutResId)
        Timber.d("onBindView: ")
        onBindView()
        Timber.d("onInitialize: ")
        onInitialize()
        Timber.d("onSetupView: ")
        onSetupView()
        Timber.d("onViewCreate: ")
        if (savedInstanceState == null) {
            val intent = intent
            if (intent != null) {
                val bundle = intent.extras
                if (bundle != null) {
                    Timber.d("restoreArguments: arguments=" + bundle)
                    onRestoreArgument(bundle)
                }
            }
            Timber.d("onPrepareInstance")
            onPrepareInstance()
        }

    }

    override
    fun onStart() {
        Timber.d("onStart: ")
        super.onStart()
    }

    override
    fun onResume() {
        Timber.d("onResume: ")
        super.onResume()
    }

    override
    fun onPostResume() {
        Timber.d("onPostResume: ")
        super.onPostResume()
        fragmentDelegate.setCanCommit(true)
    }

    override
    fun onPause() {
        Timber.d("onPause: ")
        super.onPause()
    }

    override
    fun onStop() {
        Timber.d("onStop: ")
        super.onStop()
        fragmentDelegate.setCanCommit(false)
    }

    override
    fun onDestroy() {
        Timber.d("onDestroy: ")
        super.onDestroy()
    }

    override
    fun onSaveInstanceState(outState: Bundle?) {
        Timber.d("onSaveInstanceState: outState=" + outState)
        super.onSaveInstanceState(outState)
        helper.onSaveInstanceState(outState)
    }


    override
    fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Timber.d("onRestoreInstanceState: savedInstanceState=" + savedInstanceState)
        super.onRestoreInstanceState(savedInstanceState)
        helper.onRestoreInstanceState(savedInstanceState)
        onBeforeRestoreInstanceStateView(savedInstanceState)
        helper.checkOrientation()
        onRestoreView(savedInstanceState)
    }

    override
    fun onScreenOrientationChanged(orientation: Int) {
    }

    override
    fun onScreenOrientationChangedToLandscape() {

    }

    override
    fun onScreenOrientationChangedToPortrait() {

    }

    override
    fun getCurrentFragment(): Fragment? = fragmentDelegate.getCurrentFragment()

    fun getRootView(): View = window.decorView.rootView

    override
    fun onBackPressed() {
        var isNoMoreFragment = true
        if (getCurrentFragment() != null) {
            if (getCurrentFragment() is BaseFragment) {
                isNoMoreFragment = (getCurrentFragment() as BaseFragment).onBackPressed()
            }
        }

        if (!supportFragmentManager.popBackStackImmediate() && isNoMoreFragment) {
            super.onBackPressed()
        }
    }


    open fun onBeforeRestoreInstanceStateView(savedInstanceState: Bundle) {
        Timber.d("onBeforeRestoreInstanceStateView: savedInstanceState=" + savedInstanceState)
    }

    open fun onRestoreView(savedInstanceState: Bundle) {
        Timber.d("onRestoreView: savedInstanceState=" + savedInstanceState)
    }

    open fun onRestoreArgument(bundle: Bundle) {
        Timber.d("onRestoreArgument: bundle=" + bundle)
    }

    open fun createActivityTransaction(): OpenActivityTransaction {
        return activityOpener.createTransaction()
    }

    open fun createFragmentTransaction(): OpenFragmentTransaction {
        return fragmentDelegate.createTransaction()
    }

    abstract fun setupLayoutView(): Int

    open fun onBindView() {}

    open fun onInitialize() {}

    open fun onSetupView() {}

    open fun onPrepareInstance() {}

    /* ============================ Open Activity ======================================== */

    override
    fun openActivity(targetClass: Class<*>, request: Int, data: Bundle?) {
        activityOpener.openActivity(targetClass, request, data)
    }

    override
    fun openActivityWithFinish(targetClass: Class<*>, data: Bundle?) {
        activityOpener.openActivityWithFinish(targetClass, data)
    }

    override
    fun openActivityWithAllFinish(targetClass: Class<*>, data: Bundle?) {
        activityOpener.openActivityWithAllFinish(targetClass, data)
    }

    /* ============================ Open Fragment ======================================== */
    override
    fun bindFragmentContainer(containerLayoutRes: Int) {
        fragmentDelegate.bindFragmentContainer(containerLayoutRes)
    }

    override
    fun initFragment(fragment: Fragment) {
        fragmentDelegate.initFragment(fragment)
    }

    override
    fun contain(tag: String): Boolean {
        return fragmentDelegate.contain(tag)
    }

    override
    fun clearBackStackFragmentTo(index: Int) {
        fragmentDelegate.clearBackStackFragmentTo(index)
    }

    override
    fun clearAllBackStacksFragment() {
        fragmentDelegate.clearAllBackStacksFragment()
    }

    override
    fun backPressedFragment() {
        fragmentDelegate.backPressedFragment()
    }

    override
    fun backPressedToFragment(tag: String) {
        fragmentDelegate.backPressedToFragment(tag)
    }

    override
    fun openFragmentSlideInRight(fragment: Fragment) {
        fragmentDelegate.openFragmentSlideInRight(fragment)
    }

    override
    fun openFragmentSlideInLeft(fragment: Fragment) {
        fragmentDelegate.openFragmentSlideInLeft(fragment)
    }

    override
    fun openFragment(fragment: Fragment) {
        fragmentDelegate.openFragment(fragment)
    }

}
