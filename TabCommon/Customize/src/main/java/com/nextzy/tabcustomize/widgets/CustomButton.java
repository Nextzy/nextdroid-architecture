package com.nextzy.tabcustomize.widgets;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.nextzy.library.base.delegate.CustomFontDelegate;
import com.thekhaeng.pushdownanim.PushDownAnim;



public class CustomButton extends AppCompatButton{

    private static final long CLICK_DURATION = 100L;
    private boolean isAnim;

    public CustomButton( Context context ){
        super( context );
        init( context, null );
    }

    public CustomButton( Context context, AttributeSet attrs ){
        super( context, attrs );
        initWithAttrs( attrs, 0, 0 );
        init( context, attrs );
    }

    public CustomButton( Context context, AttributeSet attrs, int defStyle ){
        super( context, attrs, defStyle );
        initWithAttrs( attrs, defStyle, 0 );
        init( context, attrs );
    }

    private void init( Context context, AttributeSet attrs ){
        if( isAnim ){
            setClickable( true );
            PushDownAnim.setOnTouchPushDownAnim( this )
                    .setScale( 0.96f );
        }
    }


    private void initWithAttrs( AttributeSet attrs, int defStyleAttr, int defStyleRes ){
        CustomFontDelegate
                .newInstance( this )
                .injectProductSansFont( attrs, getContext() );
    }


}