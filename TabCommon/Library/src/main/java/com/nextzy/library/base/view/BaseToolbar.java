package com.nextzy.library.base.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

public abstract class BaseToolbar extends Toolbar implements View.OnClickListener {

    public BaseToolbar(Context context) {
        super(context);
        setup(context);
    }

    public BaseToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setup(context);
    }

    public BaseToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(context);
    }

    public void setup(Context context) {
        inflate(context, setupLayoutView(), this);
        bindView();
        setupView();
        setInset();
    }


    private void setInset() {
        setContentInsetsAbsolute(0, 0);
        setContentInsetsRelative(0, 0);
        setPadding(0, 0, 0, 0); //require: for tablet
    }

    protected abstract int setupLayoutView();

    protected abstract void bindView();

    protected abstract void setupView();

}
