package com.nextzy.setting.view.widget;

import android.content.Context;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nextzy.setting.R;
import com.nextzy.setting.view.widget.base.TwoStatePreferenceView;


/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

public class CheckBoxPreference extends TwoStatePreferenceView
        implements View.OnClickListener{

    private TextView tvTitle;
    private TextView tvSubTitle;
    private AppCompatCheckBox checkbox;
    private View container;
    private View divider;
    private ImageView icon;

    public CheckBoxPreference( Context context, AttributeSet attrs ){
        super( context, attrs );
    }

    public CheckBoxPreference( Context context, AttributeSet attrs, int defStyleAttr ){
        super( context, attrs, defStyleAttr );
    }

    @RequiresApi( api = Build.VERSION_CODES.LOLLIPOP )
    public CheckBoxPreference( Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes ){
        super( context, attrs, defStyleAttr, defStyleRes );
    }


    @Override
    protected int setupLayoutRes(){
        return R.layout.setting_checkbox;
    }

    @Override
    protected void setupStyleables( AttributeSet attrs, int defStyleAttr, int defStyleRes ){
        super.setupStyleables( attrs, defStyleAttr, defStyleRes );
    }


    @Override
    protected void bindView(){
        container = findViewById( R.id.setting_container );
        tvTitle = (TextView) findViewById( R.id.setting_tv_title );
        tvSubTitle = (TextView) findViewById( R.id.setting_tv_sub_title );
        checkbox = (AppCompatCheckBox) findViewById( R.id.setting_checkbox );
        divider = findViewById( R.id.setting_divider );
        icon = (ImageView) findViewById( R.id.setting_ic );
    }

    @Override
    protected void setupView(){
        injectCustomFont( tvTitle, fontName );
        injectCustomFont( tvSubTitle, fontName );
        setText( tvTitle, title );
        checkbox.setChecked( isChecked );
        setChecked( isChecked );
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
        checkbox.setEnabled( isEnabled );
    }

    @Override
    protected Parcelable onSaveInstanceState(){
        Parcelable superState = super.onSaveInstanceState();
//        SavedState ss = (SavedState) onSaveChildInstanceState( new SavedState( superState ) );
        // save data here
        return superState;
    }


    @Override
    public void onRestoreInstanceState( Parcelable state ){
        if( !( state instanceof SavedState ) ){
            super.onRestoreInstanceState( state );
            return;
        }
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState( ss.getSuperState() );
        setEnabled( isEnabled );
    }

    @Override
    public void onClick( View v ){
        toggle();
    }

    @Override
    public void onCheckedChanged( boolean isChecked ){
        checkbox.setChecked( isChecked );
        if( isChecked ){
            setText( tvSubTitle, subTitleOn );
        }else{
            setText( tvSubTitle, subTitleOff );
        }
    }


    private static class SavedState extends TwoStateSavedState{

        SavedState( Parcelable superState ){
            super( superState );
        }

        private SavedState( Parcel in, ClassLoader classLoader ){
            super( in, classLoader );
        }

        @Override
        public void writeToParcel( Parcel out, int flags ){
            super.writeToParcel( out, flags );
        }

        public static final Parcelable.ClassLoaderCreator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>(){
            @Override
            public CheckBoxPreference.SavedState createFromParcel( Parcel source, ClassLoader loader ){
                return new CheckBoxPreference.SavedState( source, loader );
            }

            public CheckBoxPreference.SavedState createFromParcel( Parcel in ){
                return null;
            }


            public CheckBoxPreference.SavedState[] newArray( int size ){
                return new CheckBoxPreference.SavedState[size];
            }
        };
    }
}
