package com.nextzy.setting.view.widget.base;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.nextzy.setting.R;


/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

public abstract class DialogPreferenceView
        extends BaseSettingPreferenceView
        implements DialogInterface.OnClickListener, DialogInterface.OnDismissListener{
    private AlertDialog.Builder builder;

    private CharSequence dialogTitle;
    private CharSequence dialogMessage;
    private Drawable dialogIcon;
    private CharSequence positiveButtonText;
    private CharSequence negativeButtonText;
    private int dialogLayoutResId;

    /**
     * The dialog, if it is showing.
     */
    private Dialog mDialog;

    /**
     * Which button was clicked.
     */
    private int mWhichButtonClicked;

    public DialogPreferenceView( Context context ){
        super( context );
    }

    public DialogPreferenceView( Context context, AttributeSet attrs ){
        super( context, attrs );
    }

    public DialogPreferenceView( Context context, AttributeSet attrs, int defStyleAttr ){
        super( context, attrs, defStyleAttr );
    }

    @RequiresApi( api = Build.VERSION_CODES.LOLLIPOP )
    public DialogPreferenceView(
            Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes ){
        super( context, attrs, defStyleAttr, defStyleRes );
    }

    @Override
    @SuppressLint( "CustomViewStyleable" )
    protected void setupStyleables( AttributeSet attrs, int defStyleAttr, int defStyleRes ){
        super.setupStyleables( attrs, defStyleAttr, defStyleRes );
        final TypedArray a = getContext().obtainStyledAttributes( attrs,
                R.styleable.DialogSettingStyle, defStyleAttr, defStyleRes );
        dialogTitle = a.getString( R.styleable.DialogSettingStyle_st_dialogTitle );
        if( dialogTitle == null ){
            // Fallback on the regular title of the preference
            // (the one that is seen in the list)
            dialogTitle = title;
        }
        dialogMessage = a.getString( R.styleable.DialogSettingStyle_st_dialogMessage );
        dialogIcon = a.getDrawable( R.styleable.DialogSettingStyle_st_dialogIcon );
        positiveButtonText = a.getString( R.styleable.DialogSettingStyle_st_positiveButtonText );
        negativeButtonText = a.getString( R.styleable.DialogSettingStyle_st_negativeButtonText );
        a.recycle();
    }

    public void setDialogTitle( CharSequence dialogTitle ){
        this.dialogTitle = dialogTitle;
    }

    public void setDialogTitle( int dialogTitleResId ){
        setDialogTitle( getContext().getString( dialogTitleResId ) );
    }

    public CharSequence getDialogTitle(){
        return dialogTitle;
    }

    public void setDialogMessage( CharSequence dialogMessage ){
        this.dialogMessage = dialogMessage;
    }

    public void setDialogMessage( int dialogMessageResId ){
        setDialogMessage( getContext().getString( dialogMessageResId ) );
    }

    public CharSequence getDialogMessage(){
        return dialogMessage;
    }

    public void setDialogIcon( Drawable dialogIcon ){
        this.dialogIcon = dialogIcon;
    }

    public void setDialogIcon( @DrawableRes int dialogIconRes ){
        dialogIcon = ContextCompat.getDrawable(getContext(), dialogIconRes);
    }

    public Drawable getDialogIcon(){
        return dialogIcon;
    }

    public void setPositiveButtonText( CharSequence positiveButtonText ){
        this.positiveButtonText = positiveButtonText;
    }

    public void setPositiveButtonText( @StringRes int positiveButtonTextResId ){
        setPositiveButtonText( getContext().getString( positiveButtonTextResId ) );
    }

    public CharSequence getPositiveButtonText(){
        return positiveButtonText;
    }

    public void setNegativeButtonText( CharSequence negativeButtonText ){
        this.negativeButtonText = negativeButtonText;
    }

    public void setNegativeButtonText( @StringRes int negativeButtonTextResId ){
        setNegativeButtonText( getContext().getString( negativeButtonTextResId ) );
    }

    public CharSequence getNegativeButtonText(){
        return negativeButtonText;
    }

    public void setDialogLayoutResource( int dialogLayoutResId ){
        this.dialogLayoutResId = dialogLayoutResId;
    }

    public int getDialogLayoutResource(){
        return dialogLayoutResId;
    }


    protected void onClick(){
        if( mDialog != null && mDialog.isShowing() ) return;
        showDialog( null );
    }

    protected void showDialog( Bundle state ){
        Context context = getContext();

        mWhichButtonClicked = DialogInterface.BUTTON_NEGATIVE;

        builder = new AlertDialog.Builder( context )
                .setTitle( dialogTitle )
                .setIcon( dialogIcon )
                .setPositiveButton( positiveButtonText, this )
                .setNegativeButton( negativeButtonText, this );

        View contentView = onCreateDialogView();
        if( contentView != null ){
            onBindDialogView( contentView );
            builder.setView( contentView );
        }else{
            builder.setMessage( dialogMessage );
        }

        onPrepareDialogBuilder( builder );


        // Create the dialog
        final Dialog dialog = mDialog = builder.create();
        if( state != null ){
            dialog.onRestoreInstanceState( state );
        }
        if( needInputMethod() ){
            requestInputMethod( dialog );
        }
        dialog.setOnDismissListener( this );
        dialog.show();
    }

    protected void onBindDialogView( View view ){
    }

    protected void onPrepareDialogBuilder( AlertDialog.Builder builder ){
    }

    protected boolean needInputMethod(){
        return false;
    }

    private void requestInputMethod( Dialog dialog ){
        Window window = dialog.getWindow();
        if( window != null ){
            window.setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE );
        }
    }

    protected View onCreateDialogView(){
        if( dialogLayoutResId == 0 ){
            return null;
        }

        LayoutInflater inflater = LayoutInflater.from( builder.getContext() );
        return inflater.inflate( dialogLayoutResId, null );
    }


    @Override
    public void onClick( DialogInterface dialog, int which ){
        mWhichButtonClicked = which;
    }

    @Override
    public void onDismiss( DialogInterface dialog ){
        mDialog = null;
        onDialogClosed( mWhichButtonClicked == DialogInterface.BUTTON_POSITIVE );
    }

    protected void onDialogClosed( boolean positiveResult ){
    }

    public Dialog getDialog(){
        return mDialog;
    }

    public void onActivityDestroy(){

        if( mDialog == null || !mDialog.isShowing() ){
            return;
        }

        mDialog.dismiss();
    }

    @Override
    protected Parcelable onSaveInstanceState(){
        final Parcelable superState = super.onSaveInstanceState();
        if( mDialog == null || !mDialog.isShowing() ){
            return superState;
        }

        final SavedState myState = new SavedState( superState );
        myState.isDialogShowing = true;
        myState.dialogBundle = mDialog.onSaveInstanceState();
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
        if( myState.isDialogShowing ){
            showDialog( myState.dialogBundle );
        }
    }

    private static class SavedState extends View.BaseSavedState{
        boolean isDialogShowing;
        Bundle dialogBundle;

        public SavedState( Parcel source ){
            super( source );
            isDialogShowing = source.readInt() == 1;
            dialogBundle = source.readBundle();
        }

        @Override
        public void writeToParcel( Parcel dest, int flags ){
            super.writeToParcel( dest, flags );
            dest.writeInt( isDialogShowing ? 1 : 0 );
            dest.writeBundle( dialogBundle );
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
