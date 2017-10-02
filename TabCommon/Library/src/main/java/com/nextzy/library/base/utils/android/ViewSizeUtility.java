package com.nextzy.library.base.utils.android;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Px;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import java.lang.reflect.Method;

/**
 * Created by thekhaeng on 5/5/2017 AD.
 */

public class ViewSizeUtility{

    public interface OnGetViewSizeListener{
        void onSize( int width, int height );
    }

    public void getSize( View view, OnGetViewSizeListener listener ){
        view.getViewTreeObserver().addOnPreDrawListener( new ViewTreeObserver.OnPreDrawListener(){
            @Override
            public boolean onPreDraw(){
                if( listener != null ){
                    view.getViewTreeObserver().removeOnPreDrawListener( this );
                    listener.onSize( view.getWidth(), view.getHeight() );
                }
                return false;
            }
        } );
    }

    public void setPaddingStart( View view, @Px int paddingStart ){
        view.setPaddingRelative( paddingStart,
                view.getPaddingTop(),
                view.getPaddingEnd(),
                view.getPaddingBottom() );
    }

    public void setPaddingTop( View view, @Px int paddingTop ){
        view.setPaddingRelative( view.getPaddingStart(),
                paddingTop,
                view.getPaddingEnd(),
                view.getPaddingBottom() );
    }

    public void setPaddingEnd( View view, @Px int paddingEnd ){
        view.setPaddingRelative( view.getPaddingStart(),
                view.getPaddingTop(),
                paddingEnd,
                view.getPaddingBottom() );
    }

    public void setPaddingBottom( View view, @Px int paddingBottom ){
        view.setPaddingRelative( view.getPaddingStart(),
                view.getPaddingTop(),
                view.getPaddingEnd(),
                paddingBottom );
    }

    public interface OnCalculateSize{
        void success( @Px int width, @Px int height );
    }

    public static void calculateViewSize( View view, OnCalculateSize listener ){
        view.getViewTreeObserver().addOnPreDrawListener( new ViewTreeObserver.OnPreDrawListener(){
            @Override
            public boolean onPreDraw(){
                view.getViewTreeObserver().removeOnPreDrawListener( this );
                if( listener != null ){
                    listener.success( view.getWidth(), view.getHeight() );
                }
                return false;
            }
        } );
    }

    public static void setWidthViewSize( View view, @Px int width ){
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = width;
        view.requestLayout();
    }

    public static void setHeightViewSize( View view, @Px int height ){
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = height;
        view.requestLayout();
    }

    public static void setViewSize( View view, @Px int width, @Px int height ){
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = height;
        params.width = width;
        view.requestLayout();
    }

    public static int getDrawableId( Context context, String name ){
        int id = context.getResources().getIdentifier(
                name,
                "drawable",
                context.getPackageName() );
        return id;
    }

    public static int dpToPx( int dp ){
        return (int) ( dp * Resources.getSystem().getDisplayMetrics().density );
    }

    public static int pxToDp( @Px int px ){
        return (int) ( px / Resources.getSystem().getDisplayMetrics().density );
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
        WindowManager wm = (WindowManager) context.getSystemService( Context.WINDOW_SERVICE );
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

    public static int getActionBarSize( @NonNull Context context ){
        TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute( android.R.attr.actionBarSize, value, true );
        int actionBarSize = TypedValue.complexToDimensionPixelSize(
                value.data, context.getResources().getDisplayMetrics() );
        return actionBarSize;
    }


}
