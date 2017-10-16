package com.nextzy.tabcustomize.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;


/**
 * Created by「 The Khaeng 」on 16 Oct 2017 :)
 */

public class CustomToolbarLayout extends Toolbar{

    public CustomToolbarLayout( Context context) {
        super(context);
        setInset();
    }

    public CustomToolbarLayout( Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setInset();
    }

    public CustomToolbarLayout( Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setInset();
    }

    private void setInset() {
        setContentInsetsAbsolute(0, 0);
        setContentInsetsRelative(0, 0);
        setPadding(0, 0, 0, 0); //require: for tablet
    }


}
