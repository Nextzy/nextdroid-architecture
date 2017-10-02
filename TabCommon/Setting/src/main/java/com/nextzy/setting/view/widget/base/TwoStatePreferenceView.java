package com.nextzy.setting.view.widget.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.util.AttributeSet;

import com.nextzy.setting.R;


/**
 * Created by thekhaeng on 6/13/2017 AD.
 */

public abstract class TwoStatePreferenceView extends BaseSettingPreferenceView{

    protected CharSequence subTitleOn;
    protected CharSequence subTitleOff;
    protected boolean isChecked;
    protected boolean isCheckedSet;
    protected boolean isReverseDependentsState;

    public TwoStatePreferenceView( Context context ){
        super( context );
    }

    public TwoStatePreferenceView( Context context, AttributeSet attrs ){
        super( context, attrs );
    }

    public TwoStatePreferenceView( Context context, AttributeSet attrs, int defStyleAttr ){
        super( context, attrs, defStyleAttr );
    }

    @RequiresApi( api = Build.VERSION_CODES.LOLLIPOP )
    public TwoStatePreferenceView( Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes ){
        super( context, attrs, defStyleAttr, defStyleRes );
    }

    @Override
    @SuppressLint( "CustomViewStyleable" )
    protected void setupStyleables( AttributeSet attrs, int defStyleAttr, int defStyleRes ){
        super.setupStyleables( attrs, defStyleAttr, defStyleRes );
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.TwoStateSettingStyle, defStyleAttr, defStyleRes );
        for( int i = a.getIndexCount() - 1; i >= 0; i-- ){
            int attr = a.getIndex( i );
            if( attr == R.styleable.TwoStateSettingStyle_st_subTitleOn ){
                subTitleOn = a.getString( attr );
            }else if( attr == R.styleable.TwoStateSettingStyle_st_subTitleOff ){
                subTitleOff = a.getString( attr );
            }else if( attr == R.styleable.TwoStateSettingStyle_st_checked ){
                isChecked = a.getBoolean( attr, false );
            }else if( attr == R.styleable.TwoStateSettingStyle_st_reverseDependencyState ){
                isReverseDependentsState = a.getBoolean( attr, false );
            }
        }
        a.recycle();
    }

    @Override
    protected void restorePersistent( SharedPreferences sharedPreferences ){
        isChecked = sharedPreferences.getBoolean( key, false );
    }

    @Override
    public boolean shouldDisableDependents(){
        boolean shouldDisable = isReverseDependentsState != isChecked;
        return shouldDisable || super.shouldDisableDependents();
    }

    public void setChecked( boolean isChecked ){
        // Always persist/notify the first time; don't assume the field's default of false.
        final boolean changed = this.isChecked != isChecked;
        if( changed || !isCheckedSet ){
            this.isChecked = isChecked;
            isCheckedSet = true;
            persistBoolean( key, isChecked );
        }
        notifyDependencyChange( shouldDisableDependents() );
        onCheckedChanged( isChecked );
    }

    public boolean isChecked(){
        return isChecked;
    }

    public void toggle(){
        setChecked( !isChecked );
    }


    public abstract void onCheckedChanged( boolean isChecked );


    public void setSubTitleOn( CharSequence subTitleOn ){
        this.subTitleOn = subTitleOn;
    }

    public void setSubTitleOn( @StringRes int subTitleResId ){
        setSubTitleOn( getContext().getString( subTitleResId ) );
    }

    public CharSequence getSubTitleOn(){
        return subTitleOn;
    }

    public void setSubTitleOff( CharSequence subTitleOff ){
        this.subTitleOff = subTitleOff;
    }

    public void setSubTitleOff( @StringRes int summaryResId ){
        setSubTitleOff( getContext().getString( summaryResId ) );
    }

    public CharSequence getSummaryOff(){
        return subTitleOff;
    }

    public boolean getReverseDependentsState(){
        return isReverseDependentsState;
    }

    public void setReverseDependentsState( boolean reverseDependentsState ){
        isReverseDependentsState = reverseDependentsState;
    }

    @Override
    protected Parcelable onSaveInstanceState(){
        final Parcelable superState = super.onSaveInstanceState();
        TwoStateSavedState ss = (TwoStateSavedState) onSaveChildInstanceState( new TwoStateSavedState( superState ) );
        if( isPersistent ){
            // No need to save instance state since it's isPersistent
            return superState;
        }

        ss.isChecked = isChecked;
        ss.isCheckedSet = isCheckedSet;
        ss.isReverseDependentsState = isReverseDependentsState;
        return ss;
    }


    @Override
    protected void onRestoreInstanceState( Parcelable state ){
        if( state == null || !state.getClass().equals( TwoStateSavedState.class ) ){
            // Didn't save state for us in onSaveInstanceState
            super.onRestoreInstanceState( state );
            return;
        }

        TwoStateSavedState myState = (TwoStateSavedState) state;
        super.onRestoreInstanceState( myState.getSuperState() );
        this.isChecked = myState.isChecked;
        this.isCheckedSet = myState.isCheckedSet;
        this.isReverseDependentsState = myState.isReverseDependentsState;
        setChecked( myState.isChecked );
    }

    public static class TwoStateSavedState extends ChildSavedState{
        boolean isDefaultChecked;
        boolean isChecked;
        boolean isCheckedSet;
        boolean isReverseDependentsState;

        public TwoStateSavedState( Parcelable superState ){
            super( superState );
        }

        public TwoStateSavedState( Parcel in, ClassLoader classLoader ){
            super( in, classLoader );
            isDefaultChecked = in.readInt() == 1;
            isChecked = in.readInt() == 1;
            isCheckedSet = in.readInt() == 1;
            isReverseDependentsState = in.readInt() == 1;
        }

        @Override
        public void writeToParcel( Parcel dest, int flags ){
            super.writeToParcel( dest, flags );
            dest.writeInt( isDefaultChecked ? 1 : 0 );
            dest.writeInt( isChecked ? 1 : 0 );
            dest.writeInt( isCheckedSet ? 1 : 0 );
            dest.writeInt( isReverseDependentsState ? 1 : 0 );
        }


        public static final ClassLoaderCreator<TwoStateSavedState> CREATOR = new ClassLoaderCreator<TwoStateSavedState>(){
            @Override
            public TwoStateSavedState createFromParcel( Parcel source, ClassLoader loader ){
                return new TwoStateSavedState( source, loader );
            }

            public TwoStateSavedState createFromParcel( Parcel in ){
                return null;
            }


            public TwoStateSavedState[] newArray( int size ){
                return new TwoStateSavedState[size];
            }
        };
    }
}
