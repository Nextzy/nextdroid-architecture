package com.nextzy.setting.view.layout;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.nextzy.setting.view.widget.base.BaseSettingPreferenceView;


/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

public class SettingLayout extends LinearLayout{

    public interface OnDependencyListener{
        void onCheckDependency( String key, boolean isEnabled );
    }

    public SettingLayout( Context context ){
        super( context );
        setup( null, 0, 0 );
    }

    public SettingLayout( Context context, AttributeSet attrs ){
        super( context, attrs );
        setup( attrs, 0, 0 );
    }

    public SettingLayout( Context context, AttributeSet attrs, int defStyleAttr ){
        super( context, attrs, defStyleAttr );
        setup( attrs, defStyleAttr, 0 );
    }

    @RequiresApi( api = Build.VERSION_CODES.LOLLIPOP )
    public SettingLayout( Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes ){
        super( context, attrs, defStyleAttr, defStyleRes );
        setup( attrs, defStyleAttr, defStyleRes );
    }

    private void setup( AttributeSet attrs, int defStyleAttr, int defStyleRes ){
        setOrientation( LinearLayout.VERTICAL );
    }

    @Override
    public void onViewAdded( View child ){
        super.onViewAdded( child );
        if( child instanceof BaseSettingPreferenceView ){
            BaseSettingPreferenceView settingView = (BaseSettingPreferenceView) child;
            settingView.setOnDependencyListener( onDependencyListener() );
        }
    }

    @Override
    protected void onFinishInflate(){
        super.onFinishInflate();
        for( int i = 0; i < getChildCount(); i++ ){
            View childView = getChildAt( i );
            if( childView instanceof BaseSettingPreferenceView ){
                BaseSettingPreferenceView settingView = (BaseSettingPreferenceView) childView;
                checkDependency( settingView.getKey(), settingView.shouldDisableDependents() );
            }
        }
    }


    private void checkDependency( String key, boolean isEnabled ){
        for( int i = 0; i < getChildCount(); i++ ){
            View childView = getChildAt( i );
            if( childView instanceof BaseSettingPreferenceView ){
                BaseSettingPreferenceView settingView = (BaseSettingPreferenceView) childView;
                if( key != null && key.equals( settingView.getDependencyKey() ) ){
                    settingView.setEnabled( isEnabled );
                }
            }
        }
    }

    @NonNull
    private OnDependencyListener onDependencyListener(){
        return new OnDependencyListener(){
            @Override
            public void onCheckDependency( String key, boolean isEnabled ){
                checkDependency( key, isEnabled );
            }
        };
    }
}