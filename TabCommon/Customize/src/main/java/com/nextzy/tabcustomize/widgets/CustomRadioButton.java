package com.nextzy.tabcustomize.widgets;

import android.content.Context;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;

import com.nextzy.library.base.delegate.CustomFontDelegate;


public class CustomRadioButton extends AppCompatRadioButton{

    public CustomRadioButton( Context context) {
        super(context);
        init(context, null);
    }

    public CustomRadioButton( Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        initWithAttrs( attrs, 0, 0 );
    }

    public CustomRadioButton( Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
        initWithAttrs( attrs, defStyle, 0 );
    }

    private void init(Context context, AttributeSet attrs) {
    }

    private void initWithAttrs( AttributeSet attrs, int defStyleAttr, int defStyleRes ){
        CustomFontDelegate
                .newInstance( this )
                .injectProductSansFont( attrs, getContext() );
    }

}