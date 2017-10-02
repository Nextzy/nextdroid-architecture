package com.nextzy.setting.view.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ArrayRes;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nextzy.setting.R;
import com.nextzy.setting.view.widget.base.DialogPreferenceView;


/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

public class ListPreference extends DialogPreferenceView
        implements View.OnClickListener{

    private CharSequence[] entries;
    private CharSequence[] entryValues;
    private String value;
    private String subTitle;
    private int clickedDialogEntryIndex;
    private boolean valueSet;

    private TextView tvTitle;
    private TextView tvSubTitle;
    private View container;
    private View divider;
    private ImageView icon;

    public ListPreference( Context context ){
        super( context );
    }


    public ListPreference( Context context, AttributeSet attrs ){
        super( context, attrs );
    }

    public ListPreference( Context context, AttributeSet attrs, int defStyleAttr ){
        super( context, attrs, defStyleAttr );
    }

    @RequiresApi( api = Build.VERSION_CODES.LOLLIPOP )
    public ListPreference( Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes ){
        super( context, attrs, defStyleAttr, defStyleRes );
    }

    @Override
    protected int setupLayoutRes(){
        return R.layout.setting_list;
    }

    @Override
    @SuppressLint( "CustomViewStyleable" )
    protected void setupStyleables( AttributeSet attrs, int defStyleAttr, int defStyleRes ){
        super.setupStyleables( attrs, defStyleAttr, defStyleRes );
        TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.ListSettingStyle, defStyleAttr, defStyleRes );
        entries = a.getTextArray( R.styleable.ListSettingStyle_st_entries );
        entryValues = a.getTextArray( R.styleable.ListSettingStyle_st_entryValues );
        subTitle = a.getString( R.styleable.ListSettingStyle_st_subTitle );
        a.recycle();
    }

    @Override
    protected void restorePersistent( SharedPreferences sharedPreferences ){
        value = sharedPreferences.getString( key, null );
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
        setText( tvSubTitle, getEntry() );
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


    public void setEntries( CharSequence[] entries ){
        this.entries = entries;
    }

    public void setEntries( @ArrayRes int entriesResId ){
        setEntries( getContext().getResources().getTextArray( entriesResId ) );
    }

    public CharSequence[] getEntries(){
        return entries;
    }

    public void setEntryValues( CharSequence[] entryValues ){
        this.entryValues = entryValues;
    }

    public void setEntryValues( @ArrayRes int entryValuesResId ){
        setEntryValues( getContext().getResources().getTextArray( entryValuesResId ) );
    }

    public CharSequence[] getEntryValues(){
        return entryValues;
    }

    public void setValue( String value ){
        // Always persist/notify the first time.
        final boolean changed = !TextUtils.equals( this.value, value );
        if( changed || !valueSet ){
            this.value = value;
            valueSet = true;
            persistString( key, value );
        }
    }

    public CharSequence getSubTitle(){
        final CharSequence entry = getEntry();
        if( subTitle != null ){
            return String.format( subTitle, entry == null ? "" : entry );
        }
        return "";
    }

    public void setSubTitle( CharSequence subTitle ){
        if( subTitle == null && this.subTitle != null ){
            this.subTitle = null;
        }else if( subTitle != null && !subTitle.equals( this.subTitle ) ){
            this.subTitle = subTitle.toString();
        }
    }

    public void setValueIndex( int index ){
        if( entryValues != null ){
            setValue( entryValues[index].toString() );
        }
    }

    public String getValue(){
        return value;
    }

    public CharSequence getEntry(){
        int index = getValueIndex();
        return index >= 0 && entries != null ? entries[index] : subTitle;
    }

    public int findIndexOfValue( String value ){
        if( value != null && entryValues != null ){
            for( int i = entryValues.length - 1; i >= 0; i-- ){
                if( entryValues[i].equals( value ) ){
                    return i;
                }
            }
        }
        return -1;
    }

    private int getValueIndex(){
        return findIndexOfValue( value );
    }

    @Override
    protected void onPrepareDialogBuilder( AlertDialog.Builder builder ){
        super.onPrepareDialogBuilder( builder );

        if( entries == null || entryValues == null ){
            throw new IllegalStateException(
                    "ListPreference requires an entries array and an entryValues array." );
        }

        clickedDialogEntryIndex = getValueIndex();
        builder.setSingleChoiceItems( entries, clickedDialogEntryIndex,
                new DialogInterface.OnClickListener(){
                    public void onClick( DialogInterface dialog, int which ){
                        clickedDialogEntryIndex = which;

                        /*
                         * Clicking on an item simulates the positive button
                         * click, and dismisses the dialog.
                         */
                        ListPreference.this.onClick( dialog, DialogInterface.BUTTON_POSITIVE );
                        dialog.dismiss();
                    }
                } );

        /*
         * The typical interaction for list-based dialogs is to have
         * click-on-an-item dismiss the dialog instead of the user having to
         * press 'Ok'.
         */
        builder.setPositiveButton( null, null );
    }

    @Override
    protected void onDialogClosed( boolean positiveResult ){
        super.onDialogClosed( positiveResult );

        if( positiveResult && clickedDialogEntryIndex >= 0 && entryValues != null ){
            String value = entryValues[clickedDialogEntryIndex].toString();
            setValue( value );
            setText( tvSubTitle, getEntry() );
        }
    }

    @Override
    protected Parcelable onSaveInstanceState(){
        final Parcelable superState = super.onSaveInstanceState();
        if( isPersistent ){
            // No need to save instance state since it's persistent
            return superState;
        }

        final SavedState myState = new SavedState( superState );
        myState.value = getValue();
        return myState;
    }

    @Override
    protected void onRestoreInstanceState( Parcelable state ){
        if( state == null || !state.getClass().equals( SavedState.class ) ){
            // Didn't save state for us in onSaveInstanceState
            super.onRestoreInstanceState( state );
            return;
        }

        SavedState myState = (SavedState) state;
        super.onRestoreInstanceState( myState.getSuperState() );
        setValue( myState.value );
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick( View v ){
        showDialog( null );
    }

    private static class SavedState extends View.BaseSavedState{
        String value;

        public SavedState( Parcel source ){
            super( source );
            value = source.readString();
        }

        @Override
        public void writeToParcel( Parcel dest, int flags ){
            super.writeToParcel( dest, flags );
            dest.writeString( value );
        }

        public SavedState( Parcelable superState ){
            super( superState );
        }

        public static final Creator<SavedState> CREATOR =
                new Creator<SavedState>(){
                    public SavedState createFromParcel( Parcel in ){
                        return new SavedState( in );
                    }

                    public SavedState[] newArray( int size ){
                        return new SavedState[size];
                    }
                };
    }

}
