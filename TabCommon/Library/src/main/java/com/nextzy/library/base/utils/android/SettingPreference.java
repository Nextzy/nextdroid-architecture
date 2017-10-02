package com.nextzy.library.base.utils.android;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by thekhaeng on 1/27/2017 AD.
 */

public class SettingPreference {

    // TODO1: 2/13/2017 AD change name
    public static final String YOUR_SETTING = "<YOUR_SETTING>";
    private static SettingPreference instance;

    public static SettingPreference getInstance() {
        if (instance == null) {
            instance = new SettingPreference();
        }
        return instance;
    }

    public void saveSetting(Context context, String settingKey, boolean isCheck) {
        putBoolean(context, settingKey, isCheck);
    }

    public boolean loadSetting(Context context, String settingKey) {
        return loadSetting(context, settingKey, false);
    }

    public boolean loadSetting(Context context, String settingKey, boolean defaultCheck) {
        return getBoolean(context, settingKey, defaultCheck);
    }


    /** =========================== Private method ============================================= **/
    //<editor-fold desc="Private function folding">
    private void putBoolean(Context context, String key, boolean value) {
        SharedPreferences prefs = getSettingPreference(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    private boolean getBoolean(Context context, String key) {
        SharedPreferences prefs = getSettingPreference(context);
        return prefs.getBoolean(key, false);
    }

    private boolean getBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences prefs = getSettingPreference(context);
        return prefs.getBoolean(key, defaultValue);
    }


    private SharedPreferences getSettingPreference(Context context) {
        return context.getSharedPreferences( YOUR_SETTING, MODE_PRIVATE);
    }
    //</editor-fold>
}
