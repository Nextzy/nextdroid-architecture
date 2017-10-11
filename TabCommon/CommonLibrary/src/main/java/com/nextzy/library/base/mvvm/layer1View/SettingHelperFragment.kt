package com.nextzy.library.base.mvvm.layer1View

import android.content.SharedPreferences
import android.os.Bundle
import com.nextzy.setting.view.util.SettingPreferenceDelegate
import com.nextzy.setting.view.util.SettingPreferenceInterface

/**
 * Created by「 The Khaeng 」on 11 Oct 2017 :)
 */
abstract class SettingHelperFragment
    : BaseFragment(),
      SettingPreferenceInterface {

    private lateinit var settingDelegate: SettingPreferenceDelegate

    override
    fun onCreate(savedInstanceState: Bundle?) {
        settingDelegate = SettingPreferenceDelegate(context)
        super.onCreate(savedInstanceState)
    }

    override
    fun persistString(key: String, value: String): Boolean {
        return settingDelegate.persistString(key, value)
    }

    override
    fun getPersistedString(key: String, defaultValue: String): String {
        return settingDelegate.getPersistedString(key, defaultValue)
    }

    override
    fun persistStringSet(key: String, values: Set<String>): Boolean {
        return settingDelegate.persistStringSet(key, values)
    }

    override
    fun getPersistedStringSet(key: String, defaultValue: Set<String>): Set<String> {
        return settingDelegate.getPersistedStringSet(key, defaultValue)
    }

    override
    fun persistInt(key: String, value: Int): Boolean {
        return settingDelegate.persistInt(key, value)
    }

    override
    fun getPersistedInt(key: String, defaultValue: Int): Int {
        return settingDelegate.getPersistedInt(key, defaultValue)
    }

    override
    fun persistFloat(key: String, value: Float): Boolean {
        return settingDelegate.persistFloat(key, value)
    }

    override
    fun getPersistedFloat(key: String, defaultReturnValue: Float): Float {
        return settingDelegate.getPersistedFloat(key, defaultReturnValue)
    }

    override
    fun persistLong(key: String, value: Long): Boolean {
        return settingDelegate.persistLong(key, value)
    }

    override
    fun getPersistedLong(key: String, defaultValue: Long): Long {
        return settingDelegate.getPersistedLong(key, defaultValue)
    }

    override
    fun persistedBoolean(key: String, value: Boolean): Boolean {
        return settingDelegate.persistedBoolean(key, value)
    }

    override
    fun getPersistedBoolean(key: String, defaultValue: Boolean): Boolean {
        return settingDelegate.getPersistedBoolean(key, defaultValue)
    }

    override
    fun getSharedPreferences(): SharedPreferences {
        return settingDelegate.sharedPreferences
    }
}