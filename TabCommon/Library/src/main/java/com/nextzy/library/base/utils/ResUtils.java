package com.nextzy.library.base.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;

/**
 * Created by thekhaeng on 12/31/2016 AD.
 */

public class ResUtils{

    public static String getStringResByConst( Context c, String constance ){
        return c.getResources().getString( c.getResources().getIdentifier(
                constance,
                "string",
                c.getPackageName() ) );
    }

    public static int getColorResByConst( Context c, String constance ){
        return ContextCompat.getColor( c, c.getResources().getIdentifier(
                constance,
                "color",
                c.getPackageName() ) );
    }

    public static int getPixelFromDp( Context context, float dp ){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) ( dp * ( metrics.densityDpi / 160f ) );
    }

    public static float getDpFromPixel( Context context, float px ){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return px / ( metrics.densityDpi / 160f );
    }
}
