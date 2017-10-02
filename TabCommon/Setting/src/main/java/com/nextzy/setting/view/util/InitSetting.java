package com.nextzy.setting.view.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.StringRes;

import java.util.Set;

/**
 * Created by thekhaeng on 6/14/2017 AD.
 */

public class InitSetting{
    public static final String FIRST_RUN_APPLICATION = "first_run_application";
    private final Context context;
    private SettingPreferenceDelegate settingDelegate;
    private boolean isFirstRun = true;

    public static InitSetting init( Context context ){
        return new InitSetting( context );
    }

    private InitSetting( Context context ){
        this.context = context;
        settingDelegate = new SettingPreferenceDelegate( context );
    }

    public InitSetting persistString( String key, String value ){
        if( isFirstRun ) settingDelegate.persistString( key, value );
        return this;
    }

    public InitSetting persistString( @StringRes int stringId, String value ){
        return persistString( getString( stringId ), value );
    }

    public InitSetting persistStringSet( String key, Set<String> values ){
        if( isFirstRun ) settingDelegate.persistStringSet( key, values );
        return this;
    }

    public InitSetting persistStringSet( @StringRes int stringId, Set<String> value ){
        return persistStringSet( getString( stringId ), value );
    }

    public InitSetting persistInt( String key, int value ){
        if( isFirstRun ) settingDelegate.persistInt( key, value );
        return this;
    }

    public InitSetting persistInt( @StringRes int stringId, int value ){
        return persistInt( getString( stringId ), value );
    }

    public InitSetting persistFloat( String key, float value ){
        if( isFirstRun ) settingDelegate.persistFloat( key, value );
        return this;
    }

    public InitSetting persistFloat( @StringRes int stringId, float value ){
        return persistFloat( getString( stringId ), value );
    }

    public InitSetting persistLong( String key, long value ){
        if( isFirstRun ) settingDelegate.persistLong( key, value );
        return this;
    }

    public InitSetting persistLong( @StringRes int stringId, long value ){
        return persistLong( getString( stringId ), value );
    }

    public InitSetting persistBoolean( String key, boolean value ){
        if( isFirstRun ) settingDelegate.persistedBoolean( key, value );
        return this;
    }

    public InitSetting persistBoolean( @StringRes int stringId, boolean value ){
        return persistBoolean( getString( stringId ), value );
    }

    private String getString( @StringRes int stringId ){
        return context.getResources().getString( stringId );
    }

    public InitSetting ifFirstRunApplication(){
        isFirstRun = settingDelegate.getPersistedBoolean( FIRST_RUN_APPLICATION, true );
        if( isFirstRun ){
            SharedPreferences.Editor editor = settingDelegate.getSharedPreferences().edit();
            editor.putBoolean( FIRST_RUN_APPLICATION, false );
            editor.apply();
        }
        return this;
    }
}
