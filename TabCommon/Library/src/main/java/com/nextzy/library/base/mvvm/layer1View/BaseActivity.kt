package com.nextzy.library.base.mvvm.layer1View

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import android.support.annotation.Px
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View

import com.nextzy.library.R
import com.nextzy.library.base.delegate.ActivityHelper
import com.nextzy.library.base.delegate.ActivityHelperDelegate
import com.nextzy.library.base.delegate.FragmentHelper
import com.nextzy.library.base.delegate.FragmentHelperDelegate
import com.nextzy.library.base.delegate.OpenActivityTransaction
import com.nextzy.library.base.delegate.OpenFragmentTransaction
import com.nextzy.library.base.mvvm.exception.NotSetLayoutException
import com.nextzy.library.base.utils.android.ScreenOrientationHelper
import com.thekhaeng.settinglibrary.view.util.SettingPreferenceDelegate
import com.thekhaeng.settinglibrary.view.util.SettingPreferenceInterface

import io.reactivex.functions.Action
import timber.log.Timber


/**
 * Created by thekhaeng on 2/13/2017 AD.
 */

abstract class BaseActivity : AppCompatActivity(), ActivityHelper, FragmentHelper, ScreenOrientationHelper.ScreenOrientationChangeListener, SettingPreferenceInterface, FragmentActivityHelper {

    private val helper = ScreenOrientationHelper()
    private lateinit var activityOpener: ActivityHelperDelegate
    private lateinit var fragmentDelegate: FragmentHelperDelegate
    private var settingDelegate: SettingPreferenceDelegate? = null

    val rootView: View = window.decorView.rootView

    override val currentFragment: Fragment? = fragmentDelegate.currentFragment

    val sharedPreferences: SharedPreferences = settingDelegate.getSharedPreferences()

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        helper.setActivity(this)
        helper.onCreate(savedInstanceState)
        helper.setScreenOrientationChangeListener(this)
        settingDelegate = SettingPreferenceDelegate(this)
        val layoutResId = setupLayoutView()
        if (setupLayoutView() == 0) throw NotSetLayoutException()
        activityOpener = ActivityHelperDelegate(this)
        fragmentDelegate = FragmentHelperDelegate(this)
        Timber.d("setContentView: ")
        setContentView(layoutResId)
        Timber.d("bindView: ")
        bindView()
        Timber.d("setupInstance: ")
        setupInstance()
        Timber.d("setupView: ")
        setupView()
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
            Timber.d("initialize")
            initialize()
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
        onRestoreInstanceStateBeforeView(savedInstanceState)
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
    fun onBackPressed() {
        var isNoMoreFragment = true
        if (currentFragment != null) {
            if (currentFragment is BaseFragment) {
                isNoMoreFragment = (currentFragment as BaseFragment).onBackPressed()
            }
        }

        if (!supportFragmentManager.popBackStackImmediate() && isNoMoreFragment) {
            super.onBackPressed()
        }
    }


    protected fun onRestoreInstanceStateBeforeView(savedInstanceState: Bundle) {
        Timber.d("onRestoreInstanceStateBeforeView: savedInstanceState=" + savedInstanceState)
    }

    fun onRestoreView(savedInstanceState: Bundle) {
        Timber.d("onRestoreView: savedInstanceState=" + savedInstanceState)
    }

    fun onRestoreArgument(bundle: Bundle) {
        Timber.d("onRestoreArgument: bundle=" + bundle)
    }

    fun createActivityTransaction(): OpenActivityTransaction {
        return activityOpener.createTransaction()
    }

    fun createFragmentTransaction(): OpenFragmentTransaction {
        return fragmentDelegate.createTransaction()
    }

    abstract fun setupLayoutView(): Int

    fun bindView() {}

    protected fun setupInstance() {}

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
