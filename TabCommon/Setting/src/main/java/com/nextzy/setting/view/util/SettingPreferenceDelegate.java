package com.nextzy.setting.view.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.text.TextUtils;

import com.nextzy.setting.R;

import java.util.Set;

/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

public class SettingPreferenceDelegate implements SettingPreferenceInterface{

    private final Context context;
    private OnSettingPreferenceChangeListener listener;
    private boolean isPersistent = true;

    public interface OnSettingPreferenceChangeListener {
        void onSettingPreferenceChange( Preference preference, Object newValue );
    }

    public SettingPreferenceDelegate(Context context) {
        this.context = context;
    }

    public boolean shouldPersist() {
        return getSharedPreferences() != null && isPersistent();
    }

    public boolean isPersistent() {
        return isPersistent;
    }

    public void setPersistent(boolean persistent) {
        isPersistent = persistent;
    }

    @Override
    public boolean persistString(String key, String value) {
        return Setting.persistString(context, key, value, shouldPersist());
    }

    @Override
    public String getPersistedString(String key, String defaultValue) {
        return Setting.getPersistedString(context,key, defaultValue, shouldPersist());
    }

    @Override
    public boolean persistStringSet(String key, Set<String> values) {
        return Setting.persistStringSet(context, key, values, shouldPersist());
    }

    @Override
    public Set<String> getPersistedStringSet(String key, Set<String> defaultValue) {
        return Setting.getPersistedStringSet(context,key, defaultValue, shouldPersist());
    }

    @Override
    public boolean persistInt(String key, int value) {
        return Setting.persistInt(context,key, value, shouldPersist());
    }

    @Override
    public int getPersistedInt(String key, int defaultValue) {
        return Setting.getPersistedInt(context,key, defaultValue, shouldPersist());
    }

    @Override
    public boolean persistFloat(String key, float value) {
        return Setting.persistFloat(context,key, value, shouldPersist());
    }

    @Override
    public float getPersistedFloat(String key, float defaultReturnValue) {
        return Setting.getPersistedFloat(context,key, defaultReturnValue, shouldPersist());
    }

    @Override
    public boolean persistLong(String key, long value) {
        return Setting.persistLong(context,key, value, shouldPersist());
    }

    @Override
    public long getPersistedLong(String key, long defaultValue) {
        return Setting.getPersistedLong(context,key, defaultValue, shouldPersist());
    }

    @Override
    public boolean persistedBoolean(String key, boolean value) {
        return Setting.persistedBoolean(context,key, value, shouldPersist());
    }


    @Override
    public boolean getPersistedBoolean(String key, boolean defaultValue) {
        return Setting.getPersistedBoolean(context,key, defaultValue, shouldPersist());
    }


    @Override
    public SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private Preference.OnPreferenceChangeListener preferenceChangeListener = new Preference.OnPreferenceChangeListener(){
        @Override
        public boolean onPreferenceChange( Preference preference, Object value ){
            String stringValue = value.toString();

            if( preference instanceof ListPreference ){
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue( stringValue );

                // Set the summary to reflect the new value.
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null );

            }else if( preference instanceof RingtonePreference ){
                // For ringtone preferences, look up the correct display value
                // using RingtoneManager.
                if( TextUtils.isEmpty( stringValue ) ){
                    // Empty values correspond to 'silent' (no ringtone).
                    preference.setSummary( R.string.ringtone_silent );

                }else{
                    Ringtone ringtone = RingtoneManager.getRingtone(
                            preference.getContext(), Uri.parse( stringValue ) );

                    if( ringtone == null ){
                        // Clear the summary if there was a lookup error.
                        preference.setSummary( null );
                    }else{
                        // Set the summary to reflect the new ringtone display
                        // name.
                        String name = ringtone.getTitle( preference.getContext() );
                        preference.setSummary( name );
                    }
                }

            }else{
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.setSummary( stringValue );
            }

            if( listener != null ){
                listener.onSettingPreferenceChange( preference, value );
            }
            return true;
        }
    };

    public void setOnSettingPreferenceChange(OnSettingPreferenceChangeListener listener) {
        this.listener = listener;
    }


    /**
     * Update setting preference.
     *
     * @param preference the preference
     */
    public void updateSettingPreference(Preference preference) {
        if (preferenceChangeListener != null) {
            preference.setOnPreferenceChangeListener(preferenceChangeListener);
            // Trigger the listener immediately with the preference's
            // current value.
            preferenceChangeListener.onPreferenceChange(
                    preference,
                    PreferenceManager
                            .getDefaultSharedPreferences(preference.getContext())
                            .getString(preference.getKey(), ""));
        }
    }
}
