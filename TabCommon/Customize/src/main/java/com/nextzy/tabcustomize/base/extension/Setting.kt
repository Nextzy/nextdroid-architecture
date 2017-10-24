package com.nextzy.tabcustomize.base.extension

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import com.nextzy.setting.view.util.Setting

/**
 * Created by「 The Khaeng 」on 24 Oct 2017 :)
 */


fun Context.persistString(key: String, value: String, shouldPersist: Boolean = false): Boolean = Setting.persistString(this, key, value, shouldPersist)
fun Fragment.persistString(key: String, value: String, shouldPersist: Boolean = false): Boolean = Setting.persistString(this.context, key, value, shouldPersist)


fun Context.getPersistedString(key: String, defaultValue: String, shouldPersist: Boolean = false): String = Setting.getPersistedString(this, key, defaultValue, shouldPersist)
fun Fragment.getPersistedString(key: String, defaultValue: String, shouldPersist: Boolean = false): String = Setting.getPersistedString(this.context, key, defaultValue, shouldPersist)


fun Context.persistStringSet(key: String, values: Set<String>, shouldPersist: Boolean = false): Boolean = Setting.persistStringSet(this, key, values, shouldPersist)
fun Fragment.persistStringSet(key: String, values: Set<String>, shouldPersist: Boolean = false): Boolean = Setting.persistStringSet(this.context, key, values, shouldPersist)


fun Context.getPersistedStringSet(key: String, defaultValue: Set<String>, shouldPersist: Boolean = false): Set<String> = Setting.getPersistedStringSet(this, key, defaultValue, shouldPersist)
fun Fragment.getPersistedStringSet(key: String, defaultValue: Set<String>, shouldPersist: Boolean = false): Set<String> = Setting.getPersistedStringSet(this.context, key, defaultValue, shouldPersist)


fun Context.persistInt(key: String, value: Int, shouldPersist: Boolean = false): Boolean = Setting.persistInt(this, key, value, shouldPersist)
fun Fragment.persistInt(key: String, value: Int, shouldPersist: Boolean = false): Boolean = Setting.persistInt(this.context, key, value, shouldPersist)


fun Context.getPersistedInt(key: String, defaultValue: Int, shouldPersist: Boolean = false): Int = Setting.getPersistedInt(this, key, defaultValue, shouldPersist)
fun Fragment.getPersistedInt(key: String, defaultValue: Int, shouldPersist: Boolean = false): Int = Setting.getPersistedInt(this.context, key, defaultValue, shouldPersist)


fun Context.persistFloat(key: String, value: Float, shouldPersist: Boolean = false): Boolean = Setting.persistFloat(this, key, value, shouldPersist)
fun Fragment.persistFloat(key: String, value: Float, shouldPersist: Boolean = false): Boolean = Setting.persistFloat(this.context, key, value, shouldPersist)


fun Context.getPersistedFloat(key: String, defaultReturnValue: Float, shouldPersist: Boolean = false): Float = Setting.getPersistedFloat(this, key, defaultReturnValue, shouldPersist)
fun Fragment.getPersistedFloat(key: String, defaultReturnValue: Float, shouldPersist: Boolean = false): Float = Setting.getPersistedFloat(this.context, key, defaultReturnValue, shouldPersist)


fun Context.persistLong(key: String, value: Long, shouldPersist: Boolean = false): Boolean = Setting.persistLong(this, key, value, shouldPersist)
fun Fragment.persistLong(key: String, value: Long, shouldPersist: Boolean = false): Boolean = Setting.persistLong(this.context, key, value, shouldPersist)


fun Context.getPersistedLong(key: String, defaultValue: Long, shouldPersist: Boolean = false): Long = Setting.getPersistedLong(this, key, defaultValue, shouldPersist)
fun Fragment.getPersistedLong(key: String, defaultValue: Long, shouldPersist: Boolean = false): Long = Setting.getPersistedLong(this.context, key, defaultValue, shouldPersist)


fun Context.persistedBoolean(key: String, value: Boolean, shouldPersist: Boolean = false): Boolean = Setting.persistedBoolean(this, key, value, shouldPersist)
fun Fragment.persistedBoolean(key: String, value: Boolean, shouldPersist: Boolean = false): Boolean = Setting.persistedBoolean(this.context, key, value, shouldPersist)


fun Context.getPersistedBoolean(key: String, defaultValue: Boolean, shouldPersist: Boolean = false): Boolean = Setting.getPersistedBoolean(this, key, defaultValue, shouldPersist)
fun Fragment.getPersistedBoolean(key: String, defaultValue: Boolean, shouldPersist: Boolean = false): Boolean = Setting.getPersistedBoolean(this.context, key, defaultValue, shouldPersist)


fun Context.getSharedPreferences(): SharedPreferences? = PreferenceManager.getDefaultSharedPreferences(this)
fun Fragment.getSharedPreferences(): SharedPreferences? = PreferenceManager.getDefaultSharedPreferences(this.context)
