package com.nextzy.setting.view.layout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.TextView;

import com.nextzy.setting.R;
import com.nextzy.setting.view.util.FontUtility;


/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

public class PreferenceCategory extends SettingLayout{

    private TextView tvCategory;
    private String title;
    private int titleColorId;
    private String fontName;

    public PreferenceCategory( Context context ){
        super( context );
        setupStyleables( null, 0, 0 );
        inflateLayout();
        bindView();
    }

    public PreferenceCategory( Context context, AttributeSet attrs ){
        super( context, attrs );
        setupStyleables( attrs, 0, 0 );
        inflateLayout();
        bindView();
    }

    public PreferenceCategory( Context context, AttributeSet attrs, int defStyleAttr ){
        super( context, attrs, defStyleAttr );
        setupStyleables( attrs, defStyleAttr, 0 );
        inflateLayout();
        bindView();
    }

    @RequiresApi( api = Build.VERSION_CODES.LOLLIPOP )
    public PreferenceCategory( Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes ){
        super( context, attrs, defStyleAttr, defStyleRes );
        setupStyleables( attrs, defStyleAttr, defStyleRes );
        inflateLayout();
        bindView();
    }

    @SuppressLint( "CustomViewStyleable" )
    private void setupStyleables( AttributeSet attrs, int defStyleAttr, int defStyleRes ){
        final TypedArray a1 = getContext().obtainStyledAttributes( attrs,
                R.styleable.BaseSettingStyle, defStyleAttr, defStyleRes );
        title = a1.getString( R.styleable.BaseSettingStyle_st_title );
        fontName = a1.getString( R.styleable.BaseSettingStyle_st_font );
        a1.recycle();

        final TypedArray a2 = getContext().obtainStyledAttributes( attrs,
                R.styleable.PreferenceCategoryStyle, defStyleAttr, defStyleRes );
        titleColorId = a2.getResourceId( R.styleable.PreferenceCategoryStyle_st_titleColor, 0 );
        a2.recycle();
    }

    private void inflateLayout(){
        inflate( getContext(), R.layout.setting_category, this );
    }

    private void bindView(){
        tvCategory = (TextView) findViewById( R.id.setting_tv_category );
        tvCategory.setText( title );
        if( titleColorId != 0 )
            tvCategory.setTextColor( ContextCompat.getColor( getContext(), titleColorId ) );
        injectCustomFont( tvCategory, fontName );
    }

    public void injectCustomFont( TextView textView, String fontName ){
        if( fontName != null && !fontName.isEmpty() && !textView.isInEditMode() ){
            Typeface oldTypeface = textView.getTypeface();
            Typeface newTypeFace = FontUtility.findFont( getContext(), fontName );
            if( newTypeFace != null ){
                oldTypeface = newTypeFace;
            }
            textView.setTypeface( oldTypeface );
        }
    }

}