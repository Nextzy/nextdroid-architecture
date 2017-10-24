package com.nextzy.setting.view.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.util.Set;

/**
 * Created by「 The Khaeng 」on 24 Oct 2017 :)
 */

public final class Setting {

    public static boolean persistString(Context context, String key, String value, boolean shouldPersist) {
        if (shouldPersist) {
            // Shouldn't store null
            if (TextUtils.equals(value, getPersistedString(context, key, null, shouldPersist))) {
                // It's already there, so the same as persisting
                return true;
            }

            SharedPreferences.Editor editor = getSharedPreferences(context).edit();
            editor.putString(key, value);
            editor.apply();
            return true;
        }
        return false;
    }

    public static String getPersistedString(Context context,String key, String defaultValue, boolean shouldPersist) {
        if (!shouldPersist) {
            return defaultValue;
        }
        return getSharedPreferences(context).getString(key, defaultValue);
    }

    public static boolean persistStringSet(Context context,String key, Set<String> values, boolean shouldPersist) {
        if (shouldPersist) {
            // Shouldn't store null
            if (values.equals(getPersistedStringSet(context, key, null, shouldPersist))) {
                // It's already there, so the same as persisting
                return true;
            }

            SharedPreferences.Editor editor = getSharedPreferences(context).edit();
            editor.putStringSet(key, values);
            editor.apply();
            return true;
        }
        return false;
    }

    public static Set<String> getPersistedStringSet(Context context,String key, Set<String> defaultValue, boolean shouldPersist) {
        if (!shouldPersist) {
            return defaultValue;
        }
        return getSharedPreferences(context).getStringSet(key, defaultValue);
    }

    public static boolean persistInt(Context context, String key, int value, boolean shouldPersist) {
        if (shouldPersist) {
            if (value == getPersistedInt(context, key, ~value, shouldPersist)) {
                // It's already there, so the same as persisting
                return true;
            }

            SharedPreferences.Editor editor = getSharedPreferences(context).edit();
            editor.putInt(key, value);
            editor.apply();
            return true;
        }
        return false;
    }

    public static int getPersistedInt(Context context,String key, int defaultValue, boolean shouldPersist) {
        if (!shouldPersist) {
            return defaultValue;
        }
        return getSharedPreferences(context).getInt(key, defaultValue);
    }


    public static boolean persistFloat(Context context,String key, float value, boolean shouldPersist) {
        if (shouldPersist) {
            if (value == getPersistedFloat(context, key, Float.NaN, shouldPersist)) {
                // It's already there, so the same as persisting
                return true;
            }

            SharedPreferences.Editor editor = getSharedPreferences(context).edit();
            editor.putFloat(key, value);
            editor.apply();
            return true;
        }
        return false;
    }

    public static float getPersistedFloat(Context context,String key, float defaultReturnValue, boolean shouldPersist) {
        if (!shouldPersist) {
            return defaultReturnValue;
        }
        return getSharedPreferences(context).getFloat(key, defaultReturnValue);
    }

    public static boolean persistLong(Context context,String key, long value, boolean shouldPersist) {
        if (shouldPersist) {
            if (value == getPersistedLong(context, key, ~value, shouldPersist)) {
                // It's already there, so the same as persisting
                return true;
            }

            SharedPreferences.Editor editor = getSharedPreferences(context).edit();
            editor.putLong(key, value);
            editor.apply();
            return true;
        }
        return false;
    }

    public static long getPersistedLong(Context context,String key, long defaultValue, boolean shouldPersist) {
        if (!shouldPersist) {
            return defaultValue;
        }
        return getSharedPreferences(context).getLong(key, defaultValue);
    }

    public static boolean persistedBoolean(Context context,String key, boolean value, boolean shouldPersist) {
        if (shouldPersist) {

            if (value == getPersistedBoolean(context, key, !value, shouldPersist)) {
                // It's already there, so the same as persisting
                return true;
            }
            SharedPreferences.Editor editor = getSharedPreferences(context).edit();
            editor.putBoolean(key, value);
            editor.apply();
            return true;
        }
        return false;
    }


    public static boolean getPersistedBoolean(Context context,String key, boolean defaultValue, boolean shouldPersist) {
        if (!shouldPersist) {
            return defaultValue;
        }
        return getSharedPreferences(context).getBoolean(key, defaultValue);
    }


    public static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }


    
}
