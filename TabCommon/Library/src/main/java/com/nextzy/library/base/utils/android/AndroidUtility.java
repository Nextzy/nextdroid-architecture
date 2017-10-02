package com.nextzy.library.base.utils.android;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Method;

/**
 * Created by thekhaeng on 2/14/2017 AD.
 */

public class AndroidUtility {

    private static AndroidUtility instance;

    public static AndroidUtility getInstance(){
        if( instance == null )
            instance = new AndroidUtility();
        return instance;
    }

    public static int dpToPx( int dp ){
        return (int) ( dp * Resources.getSystem().getDisplayMetrics().density );
    }

    public static boolean isAndroid5(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static Point getActivityScreenSize( FragmentActivity activity ){
        Point point = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize( point );
        return point;
    }

    public static Point getDeviceScreenSize( Context context ){
        int resolutionX = 0, resolutionY = 0;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics( dm );

        if( Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1 ){
            try{
                Method mGetRawH = Display.class.getMethod( "getRawHeight" );
                Method mGetRawW = Display.class.getMethod( "getRawWidth" );
                resolutionX = (Integer) mGetRawW.invoke( display );
                resolutionY = (Integer) mGetRawH.invoke( display );
            }catch( Exception e ){
                resolutionX = display.getWidth();
                resolutionY = display.getHeight();
            }
        }else if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 ){
            DisplayMetrics outMetrics = new DisplayMetrics();
            display.getRealMetrics( outMetrics );

            resolutionX = outMetrics.widthPixels;
            resolutionY = outMetrics.heightPixels;
        }

        Point point = new Point();
        point.x = resolutionX;
        point.y = resolutionY;
        return point;
    }
}
