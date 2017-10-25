package com.nextzy.tabcustomize.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.nextzy.tabcustomize.base.extension.FontSizeUtility;


public class CustomEditText extends AppCompatEditText{

    public CustomEditText( Context context) {
        super(context);
        init(context, null);
    }

    public CustomEditText( Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        initWithAttrs( attrs, 0, 0 );
    }

    public CustomEditText( Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
        initWithAttrs( attrs, defStyle, 0 );
    }

    @SuppressLint( "ClickableViewAccessibility" )
    private void init( Context context, AttributeSet attrs) {
        FontSizeUtility.calculateFontSize(this);
    }

    private void initWithAttrs( AttributeSet attrs, int defStyleAttr, int defStyleRes ){
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