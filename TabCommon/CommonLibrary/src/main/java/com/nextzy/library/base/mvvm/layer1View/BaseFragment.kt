package com.nextzy.library.base.mvvm.layer1View

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nextzy.library.base.delegate.*
import com.nextzy.library.base.mvvm.exception.NotSetLayoutException
import com.nextzy.library.base.utils.android.ScreenOrientationHelper
import timber.log.Timber

/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

abstract class BaseFragment
    : Fragment(),
      ActivityHelper,
      FragmentHelper,
      ScreenOrientationHelper.ScreenOrientationChangeListener{

    private val helper = ScreenOrientationHelper()
    private lateinit var activityOpener: ActivityHelperDelegate
    private lateinit var fragmentDelegate: FragmentHelperDelegate


    override
    fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("onCreate: savedInstanceState=" + savedInstanceState)
        activityOpener = ActivityHelperDelegate(this)
        fragmentDelegate = FragmentHelperDelegate(activity)
        fragmentDelegate.setCanCommit(false)
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            val bundle = arguments
            if (bundle != null) {
                Timber.d("restoreArguments: arguments=" + bundle)
                onRestoreArgument(bundle)
            }
        } else {
            Timber.d("onRestoreInstanceState: savedInstanceState=" + savedInstanceState)
            onRestoreInstanceState(savedInstanceState)
        }
    }

    override
    fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        helper.setActivity(activity)
        helper.onCreate(savedInstanceState)
        helper.setScreenOrientationChangeListener(this)
        if (savedInstanceState != null) {
            helper.onRestoreInstanceState(savedInstanceState)
        }
        helper.checkOrientation()
    }

    override
    fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.d("onCreateView: savedInstanceState=" + savedInstanceState)
        val layoutResId = setupLayoutView()
        if (setupLayoutView() == 0) throw NotSetLayoutException()
        return inflater?.inflate(layoutResId, container, false)
    }

    override
    fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        Timber.d("onViewCreated: savedInstanceState=" + savedInstanceState)
        super.onViewCreated(view, savedInstanceState)
        Timber.d("onBindView")
        onBindView(view)
        Timber.d("onInitialize")
        onInitialize()
        Timber.d("onSetupView")
        onSetupView()
        if (savedInstanceState == null) {
            Timber.d("onPrepareInstance")
            onPrepareInstance()
        } else {
            Timber.d("onRestoreView: savedInstanceState=" + savedInstanceState)
            onRestoreView(savedInstanceState)
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
        super.onDestroy()
    }


    override
    fun onSaveInstanceState(outState: Bundle?) {
        Timber.d("saveInstanceState: oustState=" + outState)
        super.onSaveInstanceState(outState)
        helper.onSaveInstanceState(outState)
    }

    open fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Timber.d("onRestoreInstanceState: savedInstanceState=" + savedInstanceState)
    }

    open fun onRestoreView(savedInstanceState: Bundle) {
        Timber.d("onRestoreView: savedInstanceState=" + savedInstanceState)
    }

    open fun createActivityTransaction(): OpenActivityTransaction {
        return activityOpener.createTransaction()
    }

    open fun createFragmentTransaction(): OpenFragmentTransaction {
        return fragmentDelegate.createTransaction()
    }

    open fun onRestoreArgument(bundle: Bundle) {
        Timber.d("onRestoreArgument: bundle=" + bundle)
    }

    override
    fun onScreenOrientationChanged(i: Int) {

    }

    override
    fun getCurrentFragment(): Fragment? = fragmentDelegate.getCurrentFragment()

    override
    fun onScreenOrientationChangedToPortrait() {

    }

    override
    fun onScreenOrientationChangedToLandscape() {

    }

    open fun onBackPressed(): Boolean {
        return true
    }

    abstract fun setupLayoutView(): Int

    open fun onBindView(view: View?) {}

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

