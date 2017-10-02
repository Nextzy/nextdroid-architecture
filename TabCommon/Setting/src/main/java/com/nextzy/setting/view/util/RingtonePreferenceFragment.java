package com.nextzy.setting.view.util;

import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

public class RingtonePreferenceFragment extends Fragment{
    public final static String TAG = RingtonePreferenceFragment.class.getSimpleName();
    public final static int REQUEST_CODE = 1234;
    private static final String KEY_SETTING = "key_setting";
    private SettingPreferenceDelegate settingDelegate;
    private static OnPersistedRingtoneListener listener;

    public interface OnPersistedRingtoneListener{
        void onPersistedRingtoneSuccess( String name, Uri ringtoneUri );
    }

    public static RingtonePreferenceFragment newInstance( String key ){
        RingtonePreferenceFragment fragment = new RingtonePreferenceFragment();
        Bundle args = new Bundle();
        args.putString( KEY_SETTING, key );
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onAttach( Context context ){
        super.onAttach( context );
        settingDelegate = new SettingPreferenceDelegate( getContext() );
    }


    @Override
    public void onActivityResult( int requestCode, int resultCode, Intent data ){
        if( requestCode == REQUEST_CODE ){
            if( data != null ){
                Uri uri = data.getParcelableExtra( RingtoneManager.EXTRA_RINGTONE_PICKED_URI );
                String ringtoneName = data.getParcelableExtra( RingtoneManager.EXTRA_RINGTONE_TITLE );
                onSaveRingtone( getKey(), uri );
                if( listener != null ){
                    listener.onPersistedRingtoneSuccess( ringtoneName, uri );
                    listener = null;
                }
                getActivity().getSupportFragmentManager().beginTransaction().remove( this ).commit();
            }
        }else{
            super.onActivityResult( requestCode, resultCode, data );
        }
    }

    public void setOnPersistedRingtoneListener( OnPersistedRingtoneListener listener ){
        RingtonePreferenceFragment.listener = listener;
    }

    protected void onSaveRingtone( String key, Uri ringtoneUri ){
        settingDelegate.persistString( key, ringtoneUri != null ? ringtoneUri.toString() : "" );
    }

    private String getKey(){
        return getArguments().getString( KEY_SETTING );
    }

}
