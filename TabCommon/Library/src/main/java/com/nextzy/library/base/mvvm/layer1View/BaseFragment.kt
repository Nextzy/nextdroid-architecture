package com.nextzy.library.base.mvvm.layer1View

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.nextzy.library.base.delegate.ActivityHelper
import com.nextzy.library.base.delegate.ActivityHelperDelegate
import com.nextzy.library.base.delegate.FragmentHelper
import com.nextzy.library.base.delegate.FragmentHelperDelegate
import com.nextzy.library.base.delegate.OpenActivityTransaction
import com.nextzy.library.base.delegate.OpenFragmentTransaction
import com.nextzy.library.base.mvvm.exception.NotSetLayoutException
import com.nextzy.library.base.utils.android.ScreenOrientationHelper

import io.reactivex.functions.Action
import timber.log.Timber

/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

abstract class BaseFragment
    : Fragment(),
      ActivityHelper,
      FragmentHelper,
      ScreenOrientationHelper.ScreenOrientationChangeListener,
      SettingPreferenceInterface {

    private val helper = ScreenOrientationHelper()
    private lateinit var activityOpener: ActivityHelperDelegate
    private lateinit var fragmentDelegate: FragmentHelperDelegate
    private lateinit var settingDelegate: SettingPreferenceDelegate

    override val currentFragment: Fragment?
        get() = fragmentDelegate.currentFragment

    val sharedPreferences: SharedPreferences
        get() = settingDelegate.getSharedPreferences()

    override
    fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("onCreate: savedInstanceState=" + savedInstanceState!!)
        activityOpener = ActivityHelperDelegate(this)
        fragmentDelegate = FragmentHelperDelegate(activity)
        fragmentDelegate.setCanCommit(false)
        settingDelegate = SettingPreferenceDelegate(context)
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
        return inflater!!.inflate(layoutResId, container, false)
    }

    override
    fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        Timber.d("onViewCreated: savedInstanceState=" + savedInstanceState)
        super.onViewCreated(view, savedInstanceState)
        Timber.d("bindView")
        bindView(view)
        Timber.d("setupInstance")
        setupInstance()
        Timber.d("setupView")
        setupView()
        if (savedInstanceState == null) {
            Timber.d("initialize")
            initialize()
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
        Timber.d("saveInstanceState: oustState=" + outState!!)
        super.onSaveInstanceState(outState)
        helper.onSaveInstanceState(outState)
    }

    fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Timber.d("onRestoreInstanceState: savedInstanceState=" + savedInstanceState)
    }

    fun onRestoreView(savedInstanceState: Bundle) {
        Timber.d("onRestoreView: savedInstanceState=" + savedInstanceState)
    }

    fun createActivityTransaction(): OpenActivityTransaction {
        return activityOpener.createTransaction()
    }

    fun createFragmentTransaction(): OpenFragmentTransaction {
        return fragmentDelegate.createTransaction()
    }

    fun onRestoreArgument(bundle: Bundle) {
        Timber.d("onRestoreArgument: bundle=" + bundle)
    }

    override
    fun onScreenOrientationChanged(i: Int) {

    }

    override
    fun onScreenOrientationChangedToPortrait() {

    }

    override
    fun onScreenOrientationChangedToLandscape() {

    }

    fun onBackPressed(): Boolean {
        return true
    }

    abstract fun setupLayoutView(): Int

    fun bindView(view: View?) {}

    fun setupInstance() {}

    fun setupView() {}

    fun initialize() {}

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


    /* ============================== Persist =================================================== */
    fun persistString(key: String, value: String): Boolean {
        return settingDelegate.persistString(key, value)
    }

    fun getPersistedString(key: String, defaultValue: String): String {
        return settingDelegate.getPersistedString(key, defaultValue)
    }

    fun persistStringSet(key: String, values: Set<String>): Boolean {
        return settingDelegate.persistStringSet(key, values)
    }

    fun getPersistedStringSet(key: String, defaultValue: Set<String>): Set<String> {
        return settingDelegate.getPersistedStringSet(key, defaultValue)
    }

    fun persistInt(key: String, value: Int): Boolean {
        return settingDelegate.persistInt(key, value)
    }

    fun getPersistedInt(key: String, defaultValue: Int): Int {
        return settingDelegate.getPersistedInt(key, defaultValue)
    }

    fun persistFloat(key: String, value: Float): Boolean {
        return settingDelegate.persistFloat(key, value)
    }

    fun getPersistedFloat(key: String, defaultReturnValue: Float): Float {
        return settingDelegate.getPersistedFloat(key, defaultReturnValue)
    }

    fun persistLong(key: String, value: Long): Boolean {
        return settingDelegate.persistLong(key, value)
    }

    fun getPersistedLong(key: String, defaultValue: Long): Long {
        return settingDelegate.getPersistedLong(key, defaultValue)
    }

    fun persistedBoolean(key: String, value: Boolean): Boolean {
        return settingDelegate.persistedBoolean(key, value)
    }

    fun getPersistedBoolean(key: String, defaultValue: Boolean): Boolean {
        return settingDelegate.getPersistedBoolean(key, defaultValue)
    }
}

