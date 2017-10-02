package com.nextzy.setting.perference;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nextzy.setting.view.util.SettingPreferenceDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link PreferenceActivity} which implements and proxies the necessary calls
 * to be used with AppCompat.
 */
public abstract class BaseSettingPreferenceActivity extends PreferenceActivity
        implements SettingPreferenceDelegate.OnSettingPreferenceChangeListener {

    private AppCompatDelegate delegate;
    private SettingPreferenceDelegate settingDelegate = new SettingPreferenceDelegate(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getDelegate().installViewFactory();
        getDelegate().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onBuildHeaders(List<Header> target) {
        int headerSettingId = setupHeaderSettingPreference();
        int settingPreference = setupSettingPreference();
        PreferenceFragment settingFragment = setupSettingFragment();
        if (headerSettingId != -1 && setupSettingPreference() == -1 && setupSettingFragment() == null) {
            loadHeadersFromResource(headerSettingId, target);
        } else if (settingPreference != -1 && setupHeaderSettingPreference() == -1 && setupSettingFragment() == null) {
            addPreferencesFromResource(settingPreference);
            settingDelegate.setOnSettingPreferenceChange(this);
        } else if (settingFragment != null && setupSettingPreference() == -1 && setupHeaderSettingPreference() == -1) {
            getFragmentManager().beginTransaction().replace(android.R.id.content, settingFragment).commit();
        } else {
            throw new RuntimeException("Setup setting only one type.");
        }
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getDelegate().onPostCreate(savedInstanceState);
    }

    public ActionBar getSupportActionBar() {
        return getDelegate().getSupportActionBar();
    }

    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        getDelegate().setSupportActionBar(toolbar);
    }

    @Override
    public MenuInflater getMenuInflater() {
        return getDelegate().getMenuInflater();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        getDelegate().setContentView(layoutResID);
    }

    @Override
    public void setContentView(View view) {
        getDelegate().setContentView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        getDelegate().setContentView(view, params);
    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        getDelegate().addContentView(view, params);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getDelegate().onPostResume();
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        getDelegate().setTitle(title);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getDelegate().onConfigurationChanged(newConfig);
    }

    @Override
    protected void onStop() {
        super.onStop();
        getDelegate().onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getDelegate().onDestroy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }

    /**
     * This method stops fragment injection in malicious applications.
     * Make sure to deny any unknown fragments here.
     */
    @Override
    protected boolean isValidFragment(String fragmentName) {
        List<Class<?>> defaultSettingFragmentList = new ArrayList<>();
        defaultSettingFragmentList.add(PreferenceFragment.class);

        List<Class<?>> settingFragmentList = getSettingFragmentClassList();
        if (settingFragmentList != null && settingFragmentList.size() != 0) {
            defaultSettingFragmentList.addAll(settingFragmentList);
        }

        for (Class<?> settingClass : defaultSettingFragmentList) {
            if (settingClass.getName().equals(fragmentName)) {
                return true;
            }
        }
        return false;
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

    protected List<Class<?>> getSettingFragmentClassList() {
        return new ArrayList<>();
    }

    public void invalidateOptionsMenu() {
        getDelegate().invalidateOptionsMenu();
    }

    private AppCompatDelegate getDelegate() {
        if (delegate == null) {
            delegate = AppCompatDelegate.create(this, null);
        }
        return delegate;
    }

    /**
     * Helper method to determine if the device has an extra-large screen. For
     * example, 10" tablets are extra-large.
     */
    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    public void onSettingPreferenceChange(Preference preference, Object value) {
    }

    protected int setupSettingPreference() {
        return -1;
    }

    protected int setupHeaderSettingPreference() {
        return -1;
    }

    protected PreferenceFragment setupSettingFragment() {
        return null;
    }
}
