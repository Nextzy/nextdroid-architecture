package com.nextzy.nextwork.operator;

import android.util.Log;

import com.nextzy.nextwork.BuildConfig;


public class NLog{
    private static boolean showLog = BuildConfig.DEBUG;

    private static final char TOP_LEFT_CORNER = '┌';
    private static final char BOTTOM_LEFT_CORNER = '└';
    private static final char MIDDLE_CORNER = '├';
    private static final char HORIZONTAL_LINE = '│';
    private static final String DOUBLE_DIVIDER = "────────────────────────────────────────────────────────";
    private static final String SINGLE_DIVIDER = "┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄";


    public static void setShowLog( boolean showLog ){
        NLog.showLog = showLog;
    }

    public static void i( String tag, String message ){
        if( showLog ){
            Log.i( tag, TOP_LEFT_CORNER + DOUBLE_DIVIDER );
            Log.i( tag, HORIZONTAL_LINE + " " + message );
            Log.i( tag, BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER );
        }
    }

    public static void e( String tag, String message ){
        if( showLog ){
            Log.e( tag, TOP_LEFT_CORNER + DOUBLE_DIVIDER );
            Log.e( tag, HORIZONTAL_LINE + " " + message );
            Log.e( tag, BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER );
        }
    }

    public static void e( String tag, String serviceName, Throwable throwable ){
        if( showLog ){
            Log.e( tag, TOP_LEFT_CORNER + DOUBLE_DIVIDER );
            Log.e( tag, HORIZONTAL_LINE + " Throwable: " + throwable.getClass().getSimpleName() );
            Log.e( tag, HORIZONTAL_LINE + " Message: " + throwable.getMessage() );
            Log.e( tag, MIDDLE_CORNER + SINGLE_DIVIDER );
            Log.e( tag, HORIZONTAL_LINE + " Service: " + serviceName );
            Log.e( tag, BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER );
        }
    }

}
