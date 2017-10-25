package com.nextzy.tabcustomize.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ToggleButton;

import com.nextzy.tabcustomize.base.extension.FontSizeUtility;


public class CustomButtonToggle extends ToggleButton{

    public CustomButtonToggle( Context context) {
        super(context);
        init(context, null);
    }

    public CustomButtonToggle( Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        initWithAttrs( attrs, 0, 0 );
    }

    public CustomButtonToggle( Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
        initWithAttrs( attrs, defStyle, 0 );
    }

    private void init(Context context, AttributeSet attrs) {
        FontSizeUtility.calculateFontSize(this);
    }

    private void initWithAttrs( AttributeSet attrs, int defStyleAttr, int defStyleRes ){
    }

}