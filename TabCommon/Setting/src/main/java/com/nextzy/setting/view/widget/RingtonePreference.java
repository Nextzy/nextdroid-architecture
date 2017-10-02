package com.nextzy.setting.view.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nextzy.setting.R;
import com.nextzy.setting.view.util.RingtonePreferenceFragment;
import com.nextzy.setting.view.widget.base.BaseSettingPreferenceView;

/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

public class RingtonePreference extends BaseSettingPreferenceView
        implements View.OnClickListener, RingtonePreferenceFragment.OnPersistedRingtoneListener{

    private int ringtoneType;
    private boolean showDefault;
    private boolean showSilent;

    private TextView tvTitle;
    private TextView tvSubTitle;
    private View container;
    private View divider;
    private ImageView icon;

    public RingtonePreference( Context context ){
        super( context );
    }


    public RingtonePreference( Context context, AttributeSet attrs ){
        super( context, attrs );
    }

    public RingtonePreference( Context context, AttributeSet attrs, int defStyleAttr ){
        super( context, attrs, defStyleAttr );
    }

    @RequiresApi( api = Build.VERSION_CODES.LOLLIPOP )
    public RingtonePreference( Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes ){
        super( context, attrs, defStyleAttr, defStyleRes );
    }

    @Override
    @SuppressLint( "CustomViewStyleable" )
    protected void setupStyleables( AttributeSet attrs, int defStyleAttr, int defStyleRes ){
        super.setupStyleables( attrs, defStyleAttr, defStyleRes );
        final TypedArray a = getContext().obtainStyledAttributes( attrs,
                R.styleable.RingtoneSettingStyle, defStyleAttr, defStyleRes );
        ringtoneType = a.getInt( R.styleable.RingtoneSettingStyle_st_ringtoneType,
                RingtoneManager.TYPE_RINGTONE );
        showDefault = a.getBoolean( R.styleable.RingtoneSettingStyle_st_showDefault,
                true );
        showSilent = a.getBoolean( R.styleable.RingtoneSettingStyle_st_showSilent,
                true );
        a.recycle();
    }

    @Override
    protected int setupLayoutRes(){
        return R.layout.setting_ringtone;
    }

    @Override
    protected void restorePersistent( SharedPreferences sharedPreferences ){

    }

    @Override
    protected void bindView(){
        container = findViewById( R.id.setting_container );
        tvTitle = (TextView) findViewById( R.id.setting_tv_title );
        tvSubTitle = (TextView) findViewById( R.id.setting_tv_sub_title );
        divider = findViewById( R.id.setting_divider );
        icon = (ImageView) findViewById( R.id.setting_ic );
    }

    @Override
    protected void setupView(){
        injectCustomFont( tvTitle, fontName );
        injectCustomFont( tvSubTitle, fontName );
        setText( tvTitle, title );
        setText( tvSubTitle,
                getRingtoneName( Uri.parse( getPersistedString( key, "" ) ) ) );
        showView( divider, isShowDivider );
        setEnabled( isEnabled );
        container.setOnClickListener( this );

        if( iconResId != 0 ){
            icon.setVisibility( View.VISIBLE );
            icon.setBackgroundResource( iconResId );
        }else{
            icon.setVisibility( View.GONE );
        }
    }


    @Override
    public void setEnabled( boolean isEnabled ){
        super.setEnabled( isEnabled );
        icon.setEnabled( isEnabled );
        container.setEnabled( isEnabled );
        tvTitle.setEnabled( isEnabled );
        tvSubTitle.setEnabled( isEnabled );
    }

    public String getRingtoneName( Uri uri ){
        Ringtone ringtone = RingtoneManager.getRingtone( getContext(), uri );
        String name = ringtone.getTitle( getContext() );
        if( name != null && !name.isEmpty() ){
            return name;
        }else{
            return getContext().getResources().getString( R.string.ringtone_silent );
        }
    }

    public int getRingtoneType(){
        return ringtoneType;
    }

    public void setRingtoneType( int type ){
        ringtoneType = type;
    }

    public boolean getShowDefault(){
        return showDefault;
    }

    public void setShowDefault( boolean showDefault ){
        this.showDefault = showDefault;
    }

    public boolean getShowSilent(){
        return showSilent;
    }

    public void setShowSilent( boolean showSilent ){
        this.showSilent = showSilent;
    }

    protected void onPrepareRingtonePickerIntent( Intent ringtonePickerIntent ){

        ringtonePickerIntent.putExtra( RingtoneManager.EXTRA_RINGTONE_EXISTING_URI,
                onRestoreRingtone() );

        ringtonePickerIntent.putExtra( RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, showDefault );
        if( showDefault ){
            ringtonePickerIntent.putExtra( RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI,
                    RingtoneManager.getDefaultUri( getRingtoneType() ) );
        }

        ringtonePickerIntent.putExtra( RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, showSilent );
        ringtonePickerIntent.putExtra( RingtoneManager.EXTRA_RINGTONE_TYPE, ringtoneType );
        ringtonePickerIntent.putExtra( RingtoneManager.EXTRA_RINGTONE_TITLE, title );
    }


    protected Uri onRestoreRingtone(){
        final String uriString = getPersistedString( key, null );
        return !TextUtils.isEmpty( uriString ) ? Uri.parse( uriString ) : null;
    }


    @Override
    public void onClick( View v ){
        // Launch the ringtone picker
        Intent intent = new Intent( RingtoneManager.ACTION_RINGTONE_PICKER );
        onPrepareRingtonePickerIntent( intent );
        final FragmentManager fm = ( (FragmentActivity) getContext() ).getSupportFragmentManager();
        RingtonePreferenceFragment ringtoneFragment = RingtonePreferenceFragment.newInstance( key );
        ringtoneFragment.setOnPersistedRingtoneListener( this );
        fm.beginTransaction()
                .add( ringtoneFragment, RingtonePreferenceFragment.TAG )
                .commit();
        fm.executePendingTransactions();
        ringtoneFragment.startActivityForResult( intent, RingtonePreferenceFragment.REQUEST_CODE );
    }

    @Override
    public void onPersistedRingtoneSuccess( String name, Uri ringtoneUri ){
        setText( tvSubTitle, getRingtoneName( ringtoneUri ) );
    }
}
