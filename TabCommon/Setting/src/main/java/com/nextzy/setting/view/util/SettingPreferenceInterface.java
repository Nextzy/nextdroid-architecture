package com.nextzy.setting.view.util;

import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by thekhaeng on 7/8/2017 AD.
 */

public interface SettingPreferenceInterface{
    boolean persistString( String key, String value );

    String getPersistedString( String key, String defaultValue );

    boolean persistStringSet( String key, Set<String> values );

    Set<String> getPersistedStringSet( String key, Set<String> defaultValue );

    boolean persistInt( String key, int value );

    int getPersistedInt( String key, int defaultValue );

    boolean persistFloat( String key, float value );

    float getPersistedFloat( String key, float defaultReturnValue );

    boolean persistLong( String key, long value );

    long getPersistedLong( String key, long defaultValue );

    boolean persistedBoolean( String key, boolean value );

    boolean getPersistedBoolean( String key, boolean defaultValue );

    SharedPreferences getSharedPreferences();
}
