package com.nextzy.setting.view.widget.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.nextzy.setting.R;
import com.nextzy.setting.view.layout.SettingLayout;
import com.nextzy.setting.view.util.FontUtility;
import com.nextzy.setting.view.util.SettingPreferenceDelegate;

import java.util.Set;

/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

abstract public class BaseSettingPreferenceView extends FrameLayout{

    private SettingPreferenceDelegate settingDelegate = new SettingPreferenceDelegate( getContext() );
    protected int iconResId;
    protected String key;
    protected String title;
    protected boolean isEnabled = true;
    protected boolean isPersistent = true;
    protected String dependencyKey;
    protected String fontName;
    protected boolean isShowDivider = true;
    protected SettingLayout.OnDependencyListener dependencyListener;

    public BaseSettingPreferenceView( Context context ){
        super( context );
        setup( null, 0, 0 );
    }

    public BaseSettingPreferenceView( Context context, AttributeSet attrs ){
        super( context, attrs );
        setup( attrs, 0, 0 );
    }

    public BaseSettingPreferenceView( Context context, AttributeSet attrs, int defStyleAttr ){
        super( context, attrs, defStyleAttr );
        setup( attrs, defStyleAttr, 0 );
    }

    @RequiresApi( api = Build.VERSION_CODES.LOLLIPOP )
    public BaseSettingPreferenceView( Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes ){
        super( context, attrs, defStyleAttr, defStyleRes );
        setup( attrs, defStyleAttr, defStyleRes );
    }

    private void setup( AttributeSet attrs, int defStyleAttr, int defStyleRes ){
        if( attrs != null ) setupStyleables( attrs, defStyleAttr, defStyleRes );
        inflateLayout();
        restorePersistent( settingDelegate.getSharedPreferences() );
        bindView();
    }

    @Override
    protected void onFinishInflate(){
        super.onFinishInflate();
        setupView();
    }

    @SuppressLint( "CustomViewStyleable" )
    protected void setupStyleables( AttributeSet attrs, int defStyleAttr, int defStyleRes ){
        final TypedArray a1 = getContext().obtainStyledAttributes(
                attrs, R.styleable.BaseSettingStyle, defStyleAttr, defStyleRes );
        for( int i = a1.getIndexCount() - 1; i >= 0; i-- ){
            int attr = a1.getIndex( i );
            if( attr == R.styleable.BaseSettingStyle_st_title ){
                title = a1.getString( attr );
            }else if( attr == R.styleable.BaseSettingStyle_st_font ){
                fontName = a1.getString( attr );
            }
        }
        a1.recycle();

        final TypedArray a2 = getContext().obtainStyledAttributes(
                attrs, R.styleable.DefaultSettingStyle, defStyleAttr, defStyleRes );
        for( int i = a2.getIndexCount() - 1; i >= 0; i-- ){
            int attr = a2.getIndex( i );
            if( attr == R.styleable.DefaultSettingStyle_st_icon ){
                iconResId = a2.getResourceId( attr, 0 );
            }else if( attr == R.styleable.DefaultSettingStyle_st_key ){
                key = a2.getString( attr );
            }else if( attr == R.styleable.DefaultSettingStyle_st_enabled ){
                isEnabled = a2.getBoolean( attr, true );
            }else if( attr == R.styleable.DefaultSettingStyle_st_persistent ){
                isPersistent = a2.getBoolean( attr, settingDelegate.isPersistent() );
            }else if( attr == R.styleable.DefaultSettingStyle_st_dependencyKey ){
                dependencyKey = a2.getString( attr );
            }else if( attr == R.styleable.DefaultSettingStyle_st_divider ){
                isShowDivider = a2.getBoolean( attr, true );
            }
        }
        a2.recycle();
    }

    protected void setText( TextView textView, CharSequence title ){
        if( !TextUtils.isEmpty( title ) ){
            textView.setText( title );
            textView.setVisibility( View.VISIBLE );
        }else{
            textView.setVisibility( View.GONE );
        }
    }


    protected void notifyDependencyChange( boolean b ){
        if( dependencyListener != null ){
            dependencyListener.onCheckDependency( key, b );
        }
    }

    /**
     * Custom handle TwoStateSavedState child to "fix restore state in same resource id"
     * when use ViewGroup more than one.
     * <p>
     * the method must be call in subclass.
     **/
    @SuppressWarnings( "unchecked" )
    protected Parcelable onSaveChildInstanceState( ChildSavedState ss ){
        ss.childrenStates = new SparseArray();
        for( int i = 0; i < getChildCount(); i++ ){
            int id = getChildAt( i ).getId();
            if( id != 0 ){
                SparseArray childrenState = new SparseArray();
                getChildAt( i ).saveHierarchyState( childrenState );
                ss.childrenStates.put( id, childrenState );
            }

        }
        return ss;
    }

    @Override
    protected void onRestoreInstanceState( Parcelable state ){
        if( !( state instanceof ChildSavedState ) ){
            super.onRestoreInstanceState( state );
            return;
        }
        ChildSavedState ss = (ChildSavedState) state;
        super.onRestoreInstanceState( ss.getSuperState() );
        onRestoreChildInstanceState( ss );
    }

    @SuppressWarnings( "unchecked" )
    private void onRestoreChildInstanceState( ChildSavedState ss ){
        for( int i = 0; i < getChildCount(); i++ ){
            int id = getChildAt( i ).getId();
            if( id != 0 ){
                if( ss.childrenStates.get( id ) != null ){
                    SparseArray childrenState = (SparseArray) ss.childrenStates.get( id );
                    getChildAt( i ).restoreHierarchyState( childrenState );
                }
            }
        }
    }

    @Override
    protected void dispatchSaveInstanceState( SparseArray<Parcelable> container ){
        dispatchFreezeSelfOnly( container );
    }

    @Override
    protected void dispatchRestoreInstanceState( SparseArray<Parcelable> container ){
        dispatchThawSelfOnly( container );
    }

    @Override
    public void setEnabled( boolean isEnabled ){
        this.isEnabled = isEnabled;
        super.setEnabled( isEnabled );
    }

    public void setOnDependencyListener( SettingLayout.OnDependencyListener dependencyListener ){
        this.dependencyListener = dependencyListener;
    }


    public void showView( View view, boolean isShow ){
        if( isShow ){
            view.setVisibility( View.VISIBLE );
        }else{
            view.setVisibility( View.GONE );
        }
    }

    public void injectCustomFont( TextView textView, String fontName ){
        if( !textView.isInEditMode() ){
            Typeface oldTypeface = textView.getTypeface();
            Typeface newTypeFace = FontUtility.findFont( getContext(), fontName );
            if( newTypeFace != null ){
                oldTypeface = newTypeFace;
            }
            textView.setTypeface( oldTypeface );
        }
    }

    protected Drawable getSelectorBackgroundBorderless(){
        int[] attrs = new int[]{R.attr.selectableItemBackgroundBorderless /* index 0 */};
        TypedArray ta = getContext().obtainStyledAttributes( attrs );
        Drawable drawableFromTheme = ta.getDrawable( 0 /* index */ );
        ta.recycle();
        return drawableFromTheme;
    }

    public boolean shouldDisableDependents(){
        return !isEnabled();
    }

    protected boolean persistString( String key, String value ){
        return settingDelegate.persistString( key, value );
    }

    protected String getPersistedString( String key, String defaultValue ){
        return settingDelegate.getPersistedString( key, defaultValue );
    }

    public boolean persistStringSet( String key, Set<String> values ){
        return settingDelegate.persistStringSet( key, values );
    }

    public Set<String> getPersistedStringSet( String key, Set<String> defaultValue ){
        return settingDelegate.getPersistedStringSet( key, defaultValue );
    }

    protected boolean persistInt( String key, int value ){
        return settingDelegate.persistInt( key, value );
    }

    protected int getPersistedInt( String key, int defaultValue ){
        return settingDelegate.getPersistedInt( key, defaultValue );
    }

    protected boolean persistFloat( String key, float value ){
        return settingDelegate.persistFloat( key, value );
    }

    protected float getPersistedFloat( String key, float defaultReturnValue ){
        return settingDelegate.getPersistedFloat( key, defaultReturnValue );
    }

    protected boolean persistLong( String key, long value ){
        return settingDelegate.persistLong( key, value );
    }

    protected long getPersistedLong( String key, long defaultValue ){
        return settingDelegate.getPersistedLong( key, defaultValue );
    }

    protected boolean persistBoolean( String key, boolean value ){
        return settingDelegate.persistedBoolean( key, value );
    }

    protected boolean getPersistedBoolean( String key, boolean defaultValue ){
        return settingDelegate.getPersistedBoolean( key, defaultValue );
    }

    private void inflateLayout(){
        inflate( getContext(), setupLayoutRes(), this );
    }

    protected abstract int setupLayoutRes();

    protected abstract void restorePersistent( SharedPreferences sharedPreferences );

    protected abstract void bindView();

    protected abstract void setupView();


    public String getKey(){
        return key;
    }

    public String getDependencyKey(){
        return dependencyKey;
    }


    @SuppressWarnings( "unchecked" )
    public static abstract class ChildSavedState extends BaseSavedState{
        SparseArray childrenStates;

        public ChildSavedState( Parcelable superState ){
            super( superState );
        }

        public ChildSavedState( Parcel in, ClassLoader classLoader ){
            super( in );
            childrenStates = in.readSparseArray( classLoader );
        }

        @Override
        public void writeToParcel( Parcel out, int flags ){
            super.writeToParcel( out, flags );
            out.writeSparseArray( childrenStates );
        }
    }

}
