package com.nextzy.library.base.utils.android;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by「 The Khaeng 」on 18 Sep 2017 :)
 */

public class ScreenOrientationHelper{
    public static final String KEY_LAST_ORIENTATION = "last_orientation";
    private Activity activity;
    private ScreenOrientationChangeListener listener;
    private int lastOrientation;


    public interface ScreenOrientationChangeListener{
        void onScreenOrientationChanged( int orientation );

        void onScreenOrientationChangedToLandscape();

        void onScreenOrientationChangedToPortrait();
    }


    public ScreenOrientationHelper(){
    }

    public void setActivity( FragmentActivity activity ){
        this.activity = activity;
    }

    public void onCreate( Bundle savedInstanceState ){
        if( savedInstanceState == null ){
            lastOrientation = getActivity().getResources().getConfiguration().orientation;
        }
    }

    public void checkOrientation(){
        checkOrientationChanged();
    }

    public void onRestoreInstanceState( Bundle savedInstanceState ){
        restoreLastOrientationState( savedInstanceState );
    }

    public void onSaveInstanceState( Bundle outState ){
        saveLastOrientationState( outState );
    }

    private void restoreLastOrientationState( Bundle savedInstanceState ){
        lastOrientation = savedInstanceState != null ?
                savedInstanceState.getInt( KEY_LAST_ORIENTATION ) :
                getActivity().getResources().getConfiguration().orientation;
    }

    private void saveLastOrientationState( Bundle outState ){
        if( outState != null ){
            outState.putInt( KEY_LAST_ORIENTATION, lastOrientation );
        }
    }

    private void checkOrientationChanged(){
        int currentOrientation = getActivity().getResources().getConfiguration().orientation;
        if( currentOrientation != lastOrientation ){
            if( listener != null ){
                listener.onScreenOrientationChanged( currentOrientation );
                if( currentOrientation == Configuration.ORIENTATION_LANDSCAPE ){
                    listener.onScreenOrientationChangedToLandscape();
                }else if( currentOrientation == Configuration.ORIENTATION_PORTRAIT ){
                    listener.onScreenOrientationChangedToPortrait();
                }
            }
            lastOrientation = currentOrientation;
        }
    }

    public void setScreenOrientationChangeListener( ScreenOrientationChangeListener listener ){
        this.listener = listener;
    }

    private Activity getActivity(){
        if( activity == null ) throw new IllegalArgumentException( "You haven't setActivity(...)" );
        return activity;
    }
}
