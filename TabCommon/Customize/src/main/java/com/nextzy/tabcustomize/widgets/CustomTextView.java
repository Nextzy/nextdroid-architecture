package com.nextzy.tabcustomize.widgets;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.nextzy.tabcustomize.R;
import com.nextzy.tabcustomize.base.extension.FontSizeUtility;


public class CustomTextView extends AppCompatTextView{

    private int enableColor = -1;

    public CustomTextView( Context context ){
        super( context );
        init( context, null );
    }

    public CustomTextView( Context context, AttributeSet attrs ){
        super( context, attrs );
        init( context, attrs );
        initWithAttrs( attrs, 0, 0 );
    }

    public CustomTextView( Context context, AttributeSet attrs, int defStyle ){
        super( context, attrs, defStyle );
        init( context, attrs );
        initWithAttrs( attrs, defStyle, 0 );
    }

    private void init( Context context, AttributeSet attrs ){
        FontSizeUtility.calculateFontSize(this);
    }

    private void initWithAttrs( AttributeSet attrs, int defStyleAttr, int defStyleRes ){
        this.enableColor = getCurrentTextColor();
    }

    @Override
    public void setEnabled( boolean enabled ){
        super.setEnabled( enabled );
        if( enabled ){
            setTextColor( enableColor );
        }else{
            setTextColor( ContextCompat.getColor( getContext(), R.color.default_text_color_divider_black ) );
        }
    }

    public void setFontStyle( int fontStyle ){
        setTypeface( getTypeface(), fontStyle );
    }


    @Override
    public Parcelable onSaveInstanceState(){
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState( superState );
        ss.enableColor = this.enableColor;
        return ss;
    }

    @Override
    public void onRestoreInstanceState( Parcelable state ){
        if( !( state instanceof SavedState ) ){
            super.onRestoreInstanceState( state );
            return;
        }
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState( ss.getSuperState() );
        this.enableColor = ss.enableColor;
    }

    private static class SavedState extends BaseSavedState{
        int enableColor;

        SavedState( Parcelable superState ){
            super( superState );
        }

        private SavedState( Parcel in ){
            super( in );
            this.enableColor = in.readInt();
        }

        @Override
        public void writeToParcel( Parcel out, int flags ){
            super.writeToParcel( out, flags );
            out.writeInt( this.enableColor );
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>(){
            public SavedState createFromParcel( Parcel in ){
                return new SavedState( in );
            }

            public SavedState[] newArray( int size ){
                return new SavedState[size];
            }
        };
    }
}