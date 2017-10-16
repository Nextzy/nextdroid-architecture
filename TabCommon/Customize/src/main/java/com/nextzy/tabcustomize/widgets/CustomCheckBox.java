package com.nextzy.tabcustomize.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;

import com.nextzy.library.base.delegate.CustomFontDelegate;


public class CustomCheckBox extends AppCompatCheckBox{

    public CustomCheckBox( Context context) {
        super(context);
        init(context, null);
    }

    public CustomCheckBox( Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        initWithAttrs( attrs, 0, 0 );
    }

    public CustomCheckBox( Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
        initWithAttrs( attrs, defStyle, 0 );
    }

    @SuppressLint( "ClickableViewAccessibility" )
    private void init( Context context, AttributeSet attrs) {
    }

    private void initWithAttrs( AttributeSet attrs, int defStyleAttr, int defStyleRes ){
        CustomFontDelegate
                .newInstance( this )
                .injectProductSansFont( attrs, getContext() );
    }

    public void setEditable(boolean isEdit) {
        if (isEdit) {
            setEnabled(true);
            setFocusable(true);
            setFocusableInTouchMode(true);
            setClickable(true);
        } else {
            setEnabled(false);
            setFocusable(false);
            setFocusableInTouchMode(false); // user touches widget on phone with touch screen
            setClickable(false); // user navigates with wheel and selects widget
        }
    }

}