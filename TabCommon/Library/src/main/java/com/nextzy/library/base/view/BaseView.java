package com.nextzy.library.base.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import th.co.thekhaeng.waterlibrary.java.base.utils.android.ViewSizeUtility;


/**
 * Created by thekhaeng on 5/5/2017 AD.
 */

public abstract class BaseView extends View {

    private ViewSizeUtility viewSizeUtil;

    public BaseView(Context context) {
        super(context);
        init();
    }

    public BaseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        viewSizeUtil = new ViewSizeUtility();
    }

    public void getSize(ViewSizeUtility.OnGetViewSizeListener listener) {
        viewSizeUtil.getSize(this, listener);
    }
}
