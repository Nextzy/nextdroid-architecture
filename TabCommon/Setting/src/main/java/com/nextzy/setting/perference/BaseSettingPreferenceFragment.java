package com.nextzy.setting.perference;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.nextzy.setting.view.util.SettingPreferenceDelegate;


/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

public abstract class BaseSettingPreferenceFragment extends PreferenceFragment
        implements SettingPreferenceDelegate.OnSettingPreferenceChangeListener {

    private SettingPreferenceDelegate settingDelegate = new SettingPreferenceDelegate(getContext());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(getSettingPreference());
        settingDelegate.setOnSettingPreferenceChange(this);
    }

    /**
     * Update setting preference.
     *
     * @param key the key
     */
    public void updateSettingPreference(String key) {
        Preference preference = findPreference(key);
        updateSettingPreference(preference);
    }

    /**
     * Update setting preference.
     *
     * @param preference the preference
     */
    public void updateSettingPreference(Preference preference) {
        settingDelegate.updateSettingPreference(preference);
    }


    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    public void onSettingPreferenceChange(Preference preference, Object value) {
    }

    protected abstract int getSettingPreference();
}

